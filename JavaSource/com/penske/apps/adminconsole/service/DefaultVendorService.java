package com.penske.apps.adminconsole.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.SecurityDao;
import com.penske.apps.adminconsole.dao.VendorDao;
import com.penske.apps.adminconsole.model.Alert;
import com.penske.apps.adminconsole.model.User;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorContact;
import com.penske.apps.adminconsole.util.ApplicationConstants;
import com.penske.apps.suppliermgmt.model.UserContext;
import com.penske.apps.suppliermgmt.util.LookupManager;

@Service
public class DefaultVendorService implements VendorService {

	private static Logger logger = Logger.getLogger(DefaultVendorService.class);
	
	@Autowired
	private VendorDao vendorDao;
	
	@Autowired
	private SecurityDao securityDao;
	
	@Autowired
	private LookupManager lookupManager;
	
	@Override
	public List<Vendor> getAllVendors(int orgId) {
	    List<Vendor> allVendors = vendorDao.getAllVendors(orgId);
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
		
		return vendorDao.getVendorsBySearchConditions(orgId,vendor);
	}
	
	@Override
	public List<User> getAllPlanningAnalysts() {
		return vendorDao.getAllPlanningAnalysts();
	}

	@Override
	public List<User> getAllSupplySpecialists() {
		return vendorDao.getAllSupplySpecialists();
	}
	
	@Override
	public Vendor getViewVendorInformation(int vendorId) {
		return vendorDao.getViewVendorInformation(vendorId);
	}

	@Override
	public Vendor getEditVendorInformation(int vendorId) {
		return vendorDao.getEditVendorInformation(vendorId);
	}
	
	@Override
	public void modifyVendorInformation(Vendor vendor,UserContext user) {
		VendorContact primary = vendor.getPrimaryContact();
		VendorContact secondary = vendor.getSecondaryContact();
		String currentUser=user.getUserSSO();
		boolean primaryExists = false;
		boolean secondaryExists = false;
		
		validateVendor(vendor);
		vendorDao.modifyVendorInfo(vendor);
		
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
	public void sendEmailToAnalyst(Vendor vendor, UserContext user) {
		if (vendor == null || vendor.getPlanningAnalyst() == null || user == null) {
			return;
		}

		List<User> allAnalysts = getAllPlanningAnalysts();
		Optional<User> opt = allAnalysts.stream().filter(a -> a.getUserId() == vendor.getPlanningAnalyst().getUserId())
				.findFirst();
		if (!opt.isPresent()) {
			return;
		}

		User analyst = opt.get();
		if (StringUtils.isBlank(analyst.getEmail())) {
			return;
		}

		logger.info("Sending e-mail to Analyst [" + analyst.getFirstName() + " " + analyst.getLastName()
				+ "] that a new Vendor [" + vendor.getVendorName() + "] is assigned.");
		try {
			MailRequest mailRequest = new MailRequest();
			mailRequest.setUserId(user.getUserSSO());
			mailRequest.setFromAddress(
					lookupManager.getLookUpListByName(ApplicationConstants.EBS_FROM_ADDRESS).get(0).getLookUpValue());
			mailRequest.setToList(analyst.getEmail().trim());
			mailRequest.setSubject("New Vendor is Assigned");

			StringBuilder mailBody = new StringBuilder(512);
			mailBody.append("<html><body>");
			mailBody.append("<p>" + analyst.getFirstName() + ",</p>");
			mailBody.append("<p>Supplier Management Center assigned you a new Vendor.</p>");

			mailBody.append("<p><b>Vendor Name</b><br>" + vendor.getVendorName() + "</p>");
			mailBody.append("<p><b>Vendor Number</b><br>" + vendor.getVendorNumber() + "</p>");

			VendorContact primaryContact = vendor.getPrimaryContact();
			String primaryContactName = primaryContact != null
					? primaryContact.getFirstName() + " " + primaryContact.getLastName()
					: "";
			mailBody.append("<p><b>Vendor&apos;s Primary Contact</b><br>" + primaryContactName + "</p>");

			mailBody.append("<p>Please update your reports accordingly.</p>");
			mailBody.append("<p>Thank you,<br>Supplier Management Center</p>");
			mailBody.append("</body></html>");
			mailRequest.setMessageContent(mailBody.toString());

			securityDao.addEmailSent(mailRequest);// Email Content to SMC_EMAIL - uses EBS
		} catch (Exception e) {
			logger.error("E-mail sending failed for user [" + user.getUserName() + "]", e);
		}
	}

	@Override
	public void modifyVendorsMassUpdate(int[] vendorIds, Vendor vendor) {
		// Planning Analyst user ID cannot be zero or negative.
		if (vendor.getPlanningAnalyst().getUserId() <= 0) {
			return;
		}
		// Supply Specialist user ID cannot be zero or negative.
	//	else if (vendor.getSupplySpecialist().getUserId() <= 0) {
	//		return;
	//	}
		
		for (int i = 0; i < vendorIds.length; i++) {
			vendor.setVendorId(vendorIds[i]);
			
			// Vendor ID cannot be negative or 0.
			if (vendor.getVendorId() > 0) {
				vendorDao.modifyVendorInfo(vendor);
			}
		}
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
}
