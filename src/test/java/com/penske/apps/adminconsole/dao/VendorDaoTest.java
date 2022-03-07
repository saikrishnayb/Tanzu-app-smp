package com.penske.apps.adminconsole.dao;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Vendor;
import com.penske.apps.adminconsole.model.VendorContact;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

/**
 * Class under test: {@link VendorDAO}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class VendorDaoTest extends MyBatisDaoTest
{
	@Autowired
	private VendorDao dao;

	@Before
	public void setup()
	{
		dao = this.trackMethodCalls(dao, VendorDao.class);
	}

	@Test
	public void shouldGetAllPlanningAnalysts()
	{
		dao.getAllPlanningAnalysts();
	}

	@Test
	public void shouldGetAllSupplySpecialists()
	{
		dao.getAllSupplySpecialists();
	}

	@Test
	public void shouldGetVendorContact()
	{
		dao.getVendorContact("PRIMARY", 123);
	}

	@Test
	public void shouldModifyVendorInfo()
	{
		Vendor vendor = new Vendor();
		vendor.setVendorId(1234);
		vendor.setNotificationException("Y");
		vendor.setAnnualAgreement("Y");
		vendor.setPlanningAnalyst(makeUser(8976));
		vendor.setSupplySpecialist(makeUser(73464));
		
		dao.modifyVendorInfo(vendor, "TEST");
	}

	@Test
	public void shouldModifyVendorContactInfo()
	{
		VendorContact contact = new VendorContact();
		contact.setContactType("PRIMARY");
		contact.setVendorId(1234);
		contact.setFirstName("TEST");
		contact.setLastName("CONTACT");
		contact.setPhoneNumber("5555555555");
		contact.setEmail("test@penske.com");
		contact.setResponsibility(3);
		contact.setModifiedBy("TEST");
		
		dao.modifyVendorContactInfo(contact);
	}

	@Test
	public void shouldAddVendorContact()
	{
		VendorContact contact = new VendorContact();
		contact.setContactType("PRIMARY");
		contact.setVendorId(1234);
		contact.setFirstName("TEST");
		contact.setLastName("CONTACT");
		contact.setPhoneNumber("5555555555");
		contact.setEmail("test@penske.com");
		contact.setResponsibility(3);
		contact.setCreatedBy("TEST");
		
		dao.addVendorContact(contact);
	}

	@Test
	public void shouldRemoveVendorContact()
	{
		dao.removeVendorContact("PRIMARY", 1234);
	}

	@Test
	public void shouldGetAllAlerts()
	{
		dao.getAllAlerts();
	}
	
	@Test
	public void shouldGetOrgVendorAssociationsByVendorIds() {
		dao.getOrgVendorAssociationsByVendorIds(Arrays.asList(123,124));
	}
	
	@Test
	public void shouldGetVendorPoInformation() {
		dao.getVendorPoInformation(Arrays.asList(123,124));
	}

	//***** HELPER METHODS *****//
	private EditableUser makeUser(int userId)
	{
		EditableUser user = new EditableUser();
		user.setUserId(userId);
		return user;
	}
}