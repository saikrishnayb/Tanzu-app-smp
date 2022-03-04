package com.penske.apps.adminconsole.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.penske.apps.adminconsole.domain.OrgVendorAssociation;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.smccore.base.util.DateUtil;

/**
 * Represents the report generated from the users page
 */
public class VendorActivityReport {
	
	/**
	 * the report of vendor activity
	 */
	private SXSSFWorkbook vendorActivityReport;
	
	public VendorActivityReport(List<Vendor> vendors, List<VendorPoInformation> vendorPoInformations, List<EditableUser> vendorUsers, List<OrgVendorAssociation> orgVendorAssociations) {
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		SXSSFSheet vendorActivityWorkSheet = workbook.createSheet("Vendor Activity");
		SXSSFSheet vendorAccessWorkSheet = workbook.createSheet("Vendor Access");
		
		workbook.setCompressTempFiles(true);

        populateVendorActivitySheet(workbook, vendorActivityWorkSheet, vendors, vendorPoInformations);
        
        populateVendorAccessSheet(workbook, vendorAccessWorkSheet, vendors, vendorUsers, orgVendorAssociations);
        
		this.vendorActivityReport = workbook;
	}
	
	//************************************** DEFAULT METHODS **************************************//
	public SXSSFWorkbook getVendorActivityReport() {
		return vendorActivityReport;
	}
	
	//************************************** STATIC CLASSES **************************************//
	/**
	 * Represents the a row in the vendor access sheet
	 */
	private static class VendorAccessRow {
		private Vendor vendor;
		private EditableUser vendorUser;
		
		public VendorAccessRow(Vendor vendor, EditableUser vendorUser) {
			this.vendor = vendor;
			this.vendorUser = vendorUser;
		}
		
		public Vendor getVendor() {
			return vendor;
		}
		
		public EditableUser getVendorUser() {
			return vendorUser;
		}
	}
	
	/**
	 * Represents a group of rows in the vendor accesss sheet
	 */
	private class VendorAccessGroup {
		private List<VendorAccessRow> vendorAccessRows;
		
		public VendorAccessGroup(List<VendorAccessRow> vendorAccessRows) {
			this.vendorAccessRows = vendorAccessRows;
		}
		
		public List<VendorAccessRow> getVendorAccessRows() {
			return vendorAccessRows;
		}
	}
	
	//************************************** WOOKBOOK BUILDERS **************************************//
	
	//********** VENDOR ACTIVITY SHEET **********//
	private void populateVendorActivitySheet(SXSSFWorkbook workbook, SXSSFSheet worksheet, List<Vendor> vendors, List<VendorPoInformation> vendorPoInformations) {
		generateVendorActivityHeader(workbook, worksheet);
		
		Map<Integer, VendorPoInformation> vendorPoInformationByVendorNum = vendorPoInformations.stream().collect(Collectors.toMap(vpi->vpi.getVendorNumber(), vpi->vpi));
		
		int index = 1;
		for(Vendor vendor: vendors) {
			VendorPoInformation vendorPoInformation = vendorPoInformationByVendorNum.get(vendor.getVendorNumber());
			if(vendorPoInformation == null)
				vendorPoInformation = new VendorPoInformation(vendor.getVendorNumber());
			
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
        worksheet.setColumnWidth(0, 50 * 200);

        headerDataCell=headerDataRow.createCell(1);
        headerDataCell.setCellValue("Vendor ID");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(1, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(2);
        headerDataCell.setCellValue("MFR");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(2, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(3);
        headerDataCell.setCellValue("Annual Agreement");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(3, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(4);
        headerDataCell.setCellValue("Primary Contact");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(4, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(5);
        headerDataCell.setCellValue("Contact Phone");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(5, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(6);
        headerDataCell.setCellValue("Assigned Analyst");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(6, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(7);
        headerDataCell.setCellValue("Date of Last PO Issued");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(7, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(8);
        headerDataCell.setCellValue("# PO's Issued Last 3 Years");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(8, 50 * 200);
	}
	
	private void populateVendorActivityRow(SXSSFWorkbook workbook, SXSSFRow row, Vendor vendor, VendorPoInformation vendorPoInformation) {
		XSSFCellStyle cellStyle = getCellStyle(workbook);
		XSSFCellStyle dateStyle = getDateCellStyle(workbook);
		
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
        dataCell.setCellValue(vendor.getPrimaryContact() != null ? vendor.getPrimaryContact().getFirstName() + " " + vendor.getPrimaryContact().getLastName() : "");
        
        dataCell = row.createCell(5);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendor.getPrimaryContact() != null ? vendor.getPrimaryContact().getPhoneNumber() : "");
        
        dataCell = row.createCell(6);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendor.getPlanningAnalyst() != null ? vendor.getPlanningAnalyst().getFirstName() + " " + vendor.getPlanningAnalyst().getLastName() : "");
        
        dataCell = row.createCell(7);
        dataCell.setCellStyle(dateStyle);
        dataCell.setCellValue(vendorPoInformation.getLastPoDate() != null ? DateUtil.formatDateUS(vendorPoInformation.getLastPoDate()) : "");
        
        dataCell = row.createCell(8);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorPoInformation.getPosIssuedInLast3Years());
	}
	
	//********** VENDOR ACCESS SHEET **********//
	private void populateVendorAccessSheet(SXSSFWorkbook workbook, SXSSFSheet worksheet, List<Vendor> vendors, List<EditableUser> vendorUsers, List<OrgVendorAssociation> orgVendorAssociations) {
		generateVendorAccessHeader(workbook, worksheet);
		
		List<VendorAccessGroup> vendorAccessGroups = getVendorAccessRows(vendors, vendorUsers, orgVendorAssociations);
		
		int index = 1;
		for(VendorAccessGroup group: vendorAccessGroups) {
			for(VendorAccessRow vendorAccessRow : group.getVendorAccessRows()) {
				SXSSFRow row = worksheet.createRow(index);
				populateVendorAccessRow(workbook, row, vendorAccessRow);
				index++;
			}
			//empty row
			worksheet.createRow(index);
			index++;
		}
	}
	
	private List<VendorAccessGroup> getVendorAccessRows(List<Vendor> vendors, List<EditableUser> vendorUsers, List<OrgVendorAssociation> orgVendorAssociations) {
		List<VendorAccessGroup> result = new ArrayList<>();
		
		Map<Integer, List<EditableUser>> vendorUsersByOrgId = vendorUsers.stream().collect(Collectors.groupingBy(vu->vu.getOrgId()));
		Map<Integer, List<OrgVendorAssociation>> orgVendorAssociationsByVendorId = orgVendorAssociations.stream().collect(Collectors.groupingBy(ova-> ova.getVendorId()));
		
		for(Vendor vendor: vendors) {
			List<OrgVendorAssociation> orgVendorAssociationsForVendor = orgVendorAssociationsByVendorId.get(vendor.getVendorId());
			List<VendorAccessRow> vendorAccessRowsForVendor = new ArrayList<>();
			if(orgVendorAssociationsForVendor == null || orgVendorAssociationsForVendor.isEmpty())
				continue;
			
			for(OrgVendorAssociation ova: orgVendorAssociationsForVendor) {
				if(ova.getOrgId() == ApplicationConstants.PENSKE_ORG_ID)
					continue;
					
				List<EditableUser> vendorUsersInOrg = vendorUsersByOrgId.get(ova.getOrgId());
				
				if(vendorUsersInOrg == null || vendorUsersInOrg.isEmpty())
					continue;
				
				for(EditableUser vendorUser: vendorUsersInOrg) {
					vendorAccessRowsForVendor.add(new VendorAccessRow(vendor, vendorUser));
				}
			}
			if(vendorAccessRowsForVendor != null && !vendorAccessRowsForVendor.isEmpty())
				result.add(new VendorAccessGroup(vendorAccessRowsForVendor));
		}
		
		return result;
	}
	
	private void generateVendorAccessHeader(SXSSFWorkbook workbook, SXSSFSheet worksheet) {
		Row headerDataRow=worksheet.createRow(0);
		CellStyle styleHeader = getHeaderStyle(workbook);
		
		Cell headerDataCell=null;
        //Create our static headers
        headerDataCell=headerDataRow.createCell(0);
        headerDataCell.setCellValue("Vendor Name");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(0, 50 * 200);

        headerDataCell=headerDataRow.createCell(1);
        headerDataCell.setCellValue("Vendor ID");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(1, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(2);
        headerDataCell.setCellValue("First Name");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(2, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(3);
        headerDataCell.setCellValue("Last Name");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(3, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(4);
        headerDataCell.setCellValue("Email");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(4, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(5);
        headerDataCell.setCellValue("Org");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(5, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(6);
        headerDataCell.setCellValue("Created Date");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(6, 50 * 200);
        
        headerDataCell=headerDataRow.createCell(7);
        headerDataCell.setCellValue("Last Login");
        headerDataCell.setCellStyle(styleHeader);
        worksheet.setColumnWidth(7, 50 * 200);
	}
	
	private void populateVendorAccessRow(SXSSFWorkbook workbook, SXSSFRow row, VendorAccessRow vendorAccessRow) {
		XSSFCellStyle cellStyle = getCellStyle(workbook);
		
		Cell dataCell=null;
		
		dataCell = row.createCell(0);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorAccessRow.getVendor().getVendorName());
        
        dataCell = row.createCell(1);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorAccessRow.getVendor().getVendorId());
        
        dataCell = row.createCell(2);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorAccessRow.getVendorUser().getFirstName());
        
        dataCell = row.createCell(3);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorAccessRow.getVendorUser().getLastName());
        
        dataCell = row.createCell(4);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorAccessRow.getVendorUser().getEmail());
        
        dataCell = row.createCell(5);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorAccessRow.getVendorUser().getOrg());
        
        dataCell = row.createCell(6);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorAccessRow.getVendorUser().getFormattedCreatedDate());
        
        dataCell = row.createCell(7);
        dataCell.setCellStyle(cellStyle);
        dataCell.setCellValue(vendorAccessRow.getVendorUser().getFormattedLastLoginDate());
	}
	
	private CellStyle getHeaderStyle(SXSSFWorkbook workbook) {
		Font fontHeader = workbook.createFont();
		fontHeader.setFontName("Serif");
        fontHeader.setBold(true);
		
		CellStyle styleHeader = workbook.createCellStyle();
        
        styleHeader.setFont(fontHeader);
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setWrapText(true);
        
        return styleHeader;
	}
	
	private XSSFCellStyle getCellStyle(SXSSFWorkbook workbook) {
		Font fontcell = workbook.createFont();
		fontcell.setFontName("Serif");

        XSSFCellStyle dataCellEditable = (XSSFCellStyle) workbook.createCellStyle();
        
        dataCellEditable.setFont(fontcell);
        dataCellEditable.setAlignment(HorizontalAlignment.CENTER);
        dataCellEditable.setWrapText(true);
        
        return dataCellEditable;
	}
	
	private XSSFCellStyle getDateCellStyle(SXSSFWorkbook workbook) {
		Font fontcell = workbook.createFont();
		fontcell.setFontName("Serif");

        XSSFCellStyle dateCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        
        dateCellStyle.setFont(fontcell);
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dateCellStyle.setWrapText(true);
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(ApplicationConstants.DATE_FORMAT));
		
        return dateCellStyle;
	}
}
