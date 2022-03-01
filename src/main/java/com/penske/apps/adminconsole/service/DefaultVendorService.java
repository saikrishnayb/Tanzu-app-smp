package com.penske.apps.adminconsole.service;

import static java.util.stream.Collectors.toList;

import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.VendorDao;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorContact;
import com.penske.apps.adminconsole.model.VendorPoInformation;
import com.penske.apps.smccore.base.dao.EmailDAO;
import com.penske.apps.smccore.base.domain.EmailTemplate;
import com.penske.apps.smccore.base.domain.SmcEmail;
import com.penske.apps.smccore.base.domain.User;
import com.penske.apps.smccore.base.domain.enums.EmailTemplateType;
import com.penske.apps.smccore.base.util.DateUtil;

@Service
public class DefaultVendorService implements VendorService {

	private static Logger logger = LogManager.getLogger(DefaultVendorService.class);
	
	@Autowired
	private VendorDao vendorDao;
	
	@Autowired
	private EmailDAO emailDAO;
	
	@Override
	public Vendor getVendorById(int vendorId) {
	    List<Vendor> vendors = vendorDao.getVendors(null, vendorId, null);
	    if(vendors == null || vendors.isEmpty())
	    	return null;
	    
	    return vendors.get(0);
	}
	
	@Override
	public List<Vendor> getAllVendors(int orgId) {
	    List<Vendor> allVendors = vendorDao.getVendors(orgId, null, null);
		return allVendors;
	}

	@Override
	public List<Vendor> getVendorsBySearchConditions(int orgId,Vendor vendor) {
		// The Notification Exception can only be -1, 0, or 1. (This is specified on the advanced search form in vendors.jsp)
	/*	if ("N".equalsIgnoreCase(vendor.getNotificationException())) {
			return null;
		}
		// The Annual Agreement can only be -1, 0, or 1. (This is specified on the advanced search form in vendors.jsp)
		else if ("N".equalsIgnoreCase(vendor.getAnnualAgreement())) {
			return null;
		}*/
		// The Planning Analyst cannot be negative.
		if (vendor.getPlanningAnalyst().getUserId() < 0) {
			return null;
		}
		// The Supply Specialist cannot be negative.
		else if (vendor.getSupplySpecialist().getUserId() < 0) {
			return null;
		}
		
		//Sets the vendorName search parameter to all upper case allowing for case insensitivity in the search
		if(vendor.getVendorName() != null){
			vendor.setVendorName(vendor.getVendorName().toUpperCase());
		}
		
		return vendorDao.getVendors(orgId, null, vendor);
	}
	
	@Override
	public List<EditableUser> getAllPlanningAnalysts() {
		return vendorDao.getAllPlanningAnalysts();
	}

	@Override
	public List<EditableUser> getAllSupplySpecialists() {
		return vendorDao.getAllSupplySpecialists();
	}
	
	@Override
	public Vendor modifyVendorSingleUpdate(Vendor vendor, User user) {
		if(validateVendor(vendor)) return null;
		
		updateBaseVendorRecord(vendor, user);
		updateVendorContactInformation(vendor, user);
		//I am conflicted about this call; technically we could return this out of updateBaseVendorRecord
		//but grabbing it at the end ensures that we get the latest data 
		return getVendorById(vendor.getVendorId());
	}
	
	@Override
	public void modifyVendorsMassUpdate(Vendor vendor, User user, int... vendorIds) {
		// Planning Analyst user ID cannot be zero or negative.
		if (vendor.getPlanningAnalyst().getUserId() <= 0) return;		
		
		for (int i = 0; i < vendorIds.length; i++) {
			vendor.setVendorId(vendorIds[i]);
			
			// Vendor ID cannot be negative or 0.
			if (vendor.getVendorId() > 0) {
				updateBaseVendorRecord(vendor, user);
			}
		}
	}	
	
	private void updateBaseVendorRecord(Vendor vendor, User user) {
	
		int vendorId = vendor.getVendorId();
		Vendor vendorBeforeUpdate = getVendorById(vendorId);
		int prevAnalystId = vendorBeforeUpdate != null && vendorBeforeUpdate.getPlanningAnalyst() != null
				? vendorBeforeUpdate.getPlanningAnalyst().getUserId()
				: 0;

		vendorDao.modifyVendorInfo(vendor, user.getSso());
		Vendor updatedVendor = getVendorById(vendor.getVendorId());
		if (updatedVendor.getVendorId() == vendor.getVendorId()) {
			int curAnalystId = updatedVendor.getPlanningAnalyst() != null
					? updatedVendor.getPlanningAnalyst().getUserId()
					: 0;
			if (curAnalystId != 0 && curAnalystId != prevAnalystId) {
				sendEmailToAnalyst(updatedVendor, user);
			}
		}
	}
	
	private void updateVendorContactInformation(Vendor vendor, User user) {
		String currentUser = user.getSso();
		VendorContact primary = vendor.getPrimaryContact();
		VendorContact secondary = vendor.getSecondaryContact();
		boolean primaryExists = false;
		boolean secondaryExists = false;
		
		primaryExists = (vendorDao.getVendorContact("PRIMARY", vendor.getVendorId()) != null);
		secondaryExists = (vendorDao.getVendorContact("SECONDARY", vendor.getVendorId()) != null);
		
		if (primary != null) {
			primary.setCreatedBy(currentUser);
			primary.setModifiedBy(currentUser);
			// The user entered information for the primary contact and it already exists in the database.
			if (primaryExists) {
				vendorDao.modifyVendorContactInfo(primary);
			}
			// The user entered information for the primary contact and it does not exist in the database.
			else {
				vendorDao.addVendorContact(primary);
			}
		}
		// The user did not enter information for the primary contact but it exists in the database.
		// D.Roth Sept 9 2020 - can we even hit this condition?  If we get here its an error is it not?
		else if (primary == null && primaryExists) {
			vendorDao.removeVendorContact("PRIMARY", vendor.getVendorId());
		}
		
		if (secondary != null) {
			secondary.setCreatedBy(currentUser);
			secondary.setModifiedBy(currentUser);
			// The user entered information for the secondary contact and it already exists in the database.
			if (secondaryExists) {
				vendorDao.modifyVendorContactInfo(secondary);
			}
			// The user entered information for the secondary contact and it does not exist in the database.
			else {
				vendorDao.addVendorContact(secondary);
			}
		}
		// The user did not enter information for the secondary contact but it exists in the database.
		else if (secondary == null && secondaryExists) {
			vendorDao.removeVendorContact("SECONDARY", vendor.getVendorId());
		}
	}
	
	@Override
	public void sendEmailToAnalyst(Vendor vendor, User user) {
		if (vendor == null || vendor.getPlanningAnalyst() == null || user == null) {
			return;
		}

		List<EditableUser> allAnalysts = getAllPlanningAnalysts();
		Optional<EditableUser> opt = allAnalysts.stream().filter(a -> a.getUserId() == vendor.getPlanningAnalyst().getUserId())
				.findFirst();
		if (!opt.isPresent()) {
			return;
		}

		EditableUser analyst = opt.get();
		if (StringUtils.isBlank(analyst.getEmail())) {
			return;
		}

		logger.info("Sending e-mail to Analyst [" + analyst.getFirstName() + " " + analyst.getLastName()
				+ "] that a new Vendor [" + vendor.getVendorName() + "] is assigned.");
		
		VendorContact primaryContact = vendor.getPrimaryContact();
		String primaryContactName = primaryContact != null
				? primaryContact.getFirstName() + " " + primaryContact.getLastName()
				: "";
		
		List<Pair<String, String>> replacements = Arrays.asList(
			Pair.of("[ANALYST_NAME]", analyst.getFirstName()),
			Pair.of("[VENDOR_NAME]", vendor.getVendorName()),
			Pair.of("[VENDOR_NUMBER]", String.valueOf(vendor.getVendorNumber())),
			Pair.of("[PRIMARY_CONTACT_NAME]", primaryContactName)
		);
		
		EmailTemplate template = emailDAO.getEmailTemplate(EmailTemplateType.VENDOR_ASSIGNED);
		String subject = template.getActualSubject(replacements);
		String body = template.getActualBody(replacements);
		
		SmcEmail email = new SmcEmail(EmailTemplateType.VENDOR_ASSIGNED, user.getSso(), analyst.getEmail().trim(), null, null, body, subject);
		emailDAO.insertSmcEmail(email);		
	}
	
	private boolean validateVendor(Vendor vendor) {
		// Notification Exception can only be 0 (No) or 1 (Yes).
		if ("N".equalsIgnoreCase(vendor.getNotificationException())) {
			return false;
		}
		// Annual Agreement can only be 0 (No) or 1 (Yes).
		else if ("N".equalsIgnoreCase(vendor.getAnnualAgreement() )) {
			return false;
		}
		// Planning Specialist User ID cannot be 0 or negative.
		else if (vendor.getPlanningAnalyst().getUserId() <= 0) {
			return false;
		}
		// Supply Specialist User ID cannot be 0 or negative.
		else if (vendor.getSupplySpecialist().getUserId() <= 0) {
			return false;
		}
		
		// Validate the vendor contacts.
		if (!validateContactInformation(vendor.getPrimaryContact())) {
			return false;
		}
		
		if (!validateContactInformation(vendor.getSecondaryContact())) {
			return false;
		}
		
		return true;
	}
	
	private boolean validateContactInformation(VendorContact contact) {
		if (contact != null) {
			// First Name and Last Name cannot be null or blank.
			if (StringUtils.isEmpty(contact.getFirstName()) || StringUtils.isEmpty(contact.getLastName())) {
				return false;
			}
			
			// Email cannot be null or blank.
			if (StringUtils.isEmpty(contact.getEmail())) {
				// Check to make sure that the email address is a valid email address.
				if (!contact.getEmail().matches("/[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,4}/")) {
					return false;
				}
			}
			
			// Responsibility cannot be negative or 0.
			if (contact.getResponsibility() <= 0) {
				return false;
			}
			
			// Phone numbers must be numeric and cannot be greater than 10 digits.
			if (contact.getPhoneNumber() != null) {
				if (contact.getPhoneNumber().length() <= 10) {
					try {
						Integer.parseInt(contact.getPhoneNumber());
					}
					catch (NumberFormatException nfe) {
						logger.debug(nfe);
						return false;
					}
				}
				else {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public List<Alert> getAllAlerts(){
		return vendorDao.getAllAlerts();
	}
	
	@Override
	public SXSSFWorkbook exportVendorActivity(User user, List<EditableUser> vendorUsers) {

		List<Vendor> vendors = this.getAllVendors(user.getOrgId());
		
		List<VendorPoInformation> vendorPoInformationList = vendorDao.getVendorPoInformation(vendors.stream().map(v->v.getVendorNumber()).collect(Collectors.toList()));
		Map<Integer, VendorPoInformation> vendorPoInformationByVendorNum = vendorPoInformationList.stream().collect(Collectors.toMap(vpi->vpi.getVendorNumber(), vpi->vpi));
		
		List<EditableUser> sortedVendorUsers = vendorUsers.stream()
				.sorted(Comparator.comparing(vu -> vu.getVendor().getVendorName()))
				.collect(toList());
		
		return generateVendorActivityExcel(vendors, vendorPoInformationByVendorNum, sortedVendorUsers);
	}

	private SXSSFWorkbook generateVendorActivityExcel(List<Vendor> vendors, Map<Integer, VendorPoInformation> vendorPoInformationByVendorNum, List<EditableUser> vendorUsers) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		SXSSFSheet vendorActivityWorkSheet = workbook.createSheet("Vendor Activity");
		SXSSFSheet vendorAccessWorkSheet = workbook.createSheet("Vendor Access");
		
		workbook.setCompressTempFiles(true);

        workbook.getCreationHelper();
        
        populateVendorActivitySheet(workbook, vendorActivityWorkSheet, vendors, vendorPoInformationByVendorNum);
        
        populateVendorAccessSheet(workbook, vendorAccessWorkSheet, vendorUsers);
        
		return workbook;
	}
	
	//********** VENDOR ACTIVITY SHEET **********//
	private void populateVendorActivitySheet(SXSSFWorkbook workbook, SXSSFSheet worksheet, List<Vendor> vendors, Map<Integer, VendorPoInformation> vendorPoInformationByVendorNum) {
		generateVendorActivityHeader(workbook, worksheet);
		
		int index = 1;
		for(Vendor vendor: vendors) {
			VendorPoInformation vendorPoInformation = vendorPoInformationByVendorNum.get(vendor.getVendorNumber());
			if(vendorPoInformation == null)
				throw new IllegalArgumentException("Could not find vendor PO info for Vendor Number: " + vendor.getVendorNumber());
			
			SXSSFRow row = worksheet.createRow(index);
			populateVendorActivityRow(workbook, row, vendor, vendorPoInformation);
			index++;
		}
	}
	
	private void generateVendorActivityHeader(SXSSFWorkbook workbook, SXSSFSheet worksheet) {
		Row headerDataRow=worksheet.createRow(0);
		CellStyle styleHeader = getHeaderStyle(workbook);
		
		Cell headerDataCell=null;
        //Create our static headers
        headerDataCell=headerDataRow.createCell(0);
        headerDataCell.setCellValue("Vendor Name");
        headerDataCell.setCellStyle(styleHeader);

        headerDataCell=headerDataRow.createCell(1);
        headerDataCell.setCellValue("Vendor ID");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(2);
        headerDataCell.setCellValue("MFR");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(3);
        headerDataCell.setCellValue("Annual Agreement");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(4);
        headerDataCell.setCellValue("Primary Contact");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(5);
        headerDataCell.setCellValue("Contact Phone");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(6);
        headerDataCell.setCellValue("Assigned Analyst");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(7);
        headerDataCell.setCellValue("Date of Last PO Issued");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(8);
        headerDataCell.setCellValue("# PO's Issued Last 3 Years");
        headerDataCell.setCellStyle(styleHeader);
	}
	
	private void populateVendorActivityRow(SXSSFWorkbook workbook, SXSSFRow row, Vendor vendor, VendorPoInformation vendorPoInformation) {
		XSSFCellStyle cellStyle = getCellStyle(workbook);
		
		Cell dataCell=null;
		
		dataCell = row.createCell(0);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendor.getVendorName());
        
        dataCell = row.createCell(1);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendor.getVendorId());
        
        dataCell = row.createCell(2);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(String.join(", ", vendor.getMfrCodes()));
        
        dataCell = row.createCell(3);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue("Y".equals(vendor.getAnnualAgreement()) ? "Yes" : "No");
        
        dataCell = row.createCell(4);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendor.getPrimaryContact().getFirstName() + " " + vendor.getPrimaryContact().getLastName());
        
        dataCell = row.createCell(5);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendor.getPrimaryContact().getPhoneNumber());
        
        dataCell = row.createCell(6);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendor.getPlanningAnalyst().getFirstName() + " " + vendor.getPlanningAnalyst().getLastName());
        
        dataCell = row.createCell(7);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(DateUtil.formatDateUS(vendorPoInformation.getLastPoDate()));
        
        dataCell = row.createCell(8);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorPoInformation.getPosIssuedInLast3Years());
	}
	
	//********** VENDOR ACCESS SHEET **********//
	private void populateVendorAccessSheet(SXSSFWorkbook workbook, SXSSFSheet worksheet, List<EditableUser> vendorUsers) {
		generateVendorAccessHeader(workbook, worksheet);
		
		int index = 1;
		for(EditableUser vendorUser: vendorUsers) {
			SXSSFRow row = worksheet.createRow(index);
			populateVendorAccessRow(workbook, row, vendorUser);
			index++;
		}
	}
	
	private void generateVendorAccessHeader(SXSSFWorkbook workbook, SXSSFSheet worksheet) {
		Row headerDataRow=worksheet.createRow(0);
		CellStyle styleHeader = getHeaderStyle(workbook);
		
		Cell headerDataCell=null;
        //Create our static headers
        headerDataCell=headerDataRow.createCell(0);
        headerDataCell.setCellValue("Vendor Name");
        headerDataCell.setCellStyle(styleHeader);

        headerDataCell=headerDataRow.createCell(1);
        headerDataCell.setCellValue("Vendor ID");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(2);
        headerDataCell.setCellValue("First Name");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(3);
        headerDataCell.setCellValue("Last Name");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(4);
        headerDataCell.setCellValue("Email");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(5);
        headerDataCell.setCellValue("Org");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(6);
        headerDataCell.setCellValue("Created Date");
        headerDataCell.setCellStyle(styleHeader);
        
        headerDataCell=headerDataRow.createCell(7);
        headerDataCell.setCellValue("Last Login");
        headerDataCell.setCellStyle(styleHeader);
	}
	
	private void populateVendorAccessRow(SXSSFWorkbook workbook, SXSSFRow row, EditableUser vendorUser) {
		XSSFCellStyle cellStyle = getCellStyle(workbook);
		
		Cell dataCell=null;
		
		dataCell = row.createCell(0);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorUser.getVendor().getVendorName());
        
        dataCell = row.createCell(1);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorUser.getVendor().getVendorId());
        
        dataCell = row.createCell(2);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorUser.getFirstName());
        
        dataCell = row.createCell(3);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorUser.getLastName());
        
        dataCell = row.createCell(4);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorUser.getEmail());
        
        dataCell = row.createCell(5);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorUser.getOrg());
        
        dataCell = row.createCell(6);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorUser.getFormattedCreatedDate());
        
        dataCell = row.createCell(7);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorUser.getFormattedLastLoginDate());
	}
	
	private CellStyle getHeaderStyle(SXSSFWorkbook workbook) {
		Font fontHeader = workbook.createFont();
		fontHeader.setFontName("Serif");
        fontHeader.setBold(true);
		
		CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.setBorderLeft(BorderStyle.THIN);
        styleHeader.setBorderRight(BorderStyle.THIN);
        styleHeader.setBorderTop(BorderStyle.THIN);
        styleHeader.setBorderBottom(BorderStyle.THIN);
        
        styleHeader.setFont(fontHeader);
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setVerticalAlignment(VerticalAlignment.BOTTOM);
        styleHeader.setWrapText(true);
        
        return styleHeader;
	}
	
	private XSSFCellStyle getCellStyle(SXSSFWorkbook workbook) {
		Font fontcell = workbook.createFont();

        XSSFColor lightGrey = new XSSFColor(new Color(215, 216, 217));

        XSSFCellStyle dataCellEditable = (XSSFCellStyle) workbook.createCellStyle();
        dataCellEditable.setLocked(false);
        dataCellEditable.setFillForegroundColor((short) 41);
        dataCellEditable.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        dataCellEditable.setBorderLeft(BorderStyle.THIN);
        dataCellEditable.setBorderRight(BorderStyle.THIN);
        dataCellEditable.setBorderTop(BorderStyle.THIN);
        dataCellEditable.setBorderBottom(BorderStyle.THIN);
        dataCellEditable.setBorderColor(BorderSide.LEFT, lightGrey);
        dataCellEditable.setBorderColor(BorderSide.RIGHT, lightGrey);
        dataCellEditable.setBorderColor(BorderSide.BOTTOM, lightGrey);
        dataCellEditable.setBorderColor(BorderSide.TOP, lightGrey);
        dataCellEditable.setFont(fontcell);
        
        return dataCellEditable;
	}
	
}
