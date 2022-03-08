/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.adminconsole.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.model.AdminConsoleUserDept;
import com.penske.apps.adminconsole.model.AdminConsoleUserType;
import com.penske.apps.adminconsole.model.EditableUser;
import com.penske.apps.adminconsole.model.Org;
import com.penske.apps.adminconsole.model.Role;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

/**
 * Class under test: {@link SecurityDAO}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class SecurityDAOTest extends MyBatisDaoTest
{
	@Autowired
	private SecurityDAO dao;

	@Before
	public void before()
	{
		dao = this.trackMethodCalls(dao, SecurityDAO.class);
	}
	
	@Test
	public void shouldgetPenskeUserInfo()
	{
		dao.getPenskeUserInfo(1234);
	}
	
	@Test
	public void shouldgetVendorUserInfo()
	{
		dao.getVendorUserInfo(1234);
	}
	
	@Test
	public void shouldgetUser()
	{
		dao.getUser(1234);
	}
	
	@Test
	public void shouldgetAllVendorNames()
	{
		dao.getAllVendorNames();
	}
	
	@Test
	public void shouldgetPermissions()
	{
		dao.getPermissions(5);
	}
	
	@Test
	public void shouldgetAllTabNames()
	{
		dao.getAllTabNames();
	}
	
	@Test
	public void shouldgetAllUserDepts()
	{
		dao.getAllUserDepts();
	}
	
	@Test
	public void shouldgetAllUserTypes()
	{
		dao.getAllUserTypes();
	}
	
	@Test
	public void shouldgetInitialsImage()
	{
		dao.getInitialsImage(123);
	}
	
	@Test
	public void shouldgetSignatureImage()
	{
		dao.getSignatureImage(123);
	}
	
	@Test
	public void shouldgetUserName()
	{
		dao.getUserName("600555555", 0);
		dao.getUserName("600555555", 57);
	}
	
	@Test
	public void shouldmodifyUserInfo()
	{
		dao.modifyUserInfo(makeUser(468, "600555555", UserType.PENSKE));
	}
	
	@Test
	public void shouldmodifyPenskeUser()
	{
		EditableUser user = makeUser(243, "600555555", UserType.PENSKE);
		user.setInitString("ABCD");
		user.setSignString("ABCD");
		
		dao.modifyPenskeUser(user);
	}
	
	@Test
	public void shouldaddUser()
	{
		EditableUser user = makeUser(243, "600555555", UserType.PENSKE);
		dao.addUser(user);
	}
	
	@Test
	public void shouldmodifyUserStatus()
	{
		dao.modifyUserStatus(435453, "600555555");
	}
	
	@Test
	public void shouldaddUserInitials()
	{
		EditableUser user = makeUser(243, "600555555", UserType.PENSKE);
		user.setInitString("ABCD");
		user.setSignString("ABCD");
		dao.addUserInitials(user);
	}
	
	@Test
	public void shouldrefreshUserWithSSOData()
	{
		EditableUser user = makeUser(243, "600555555", UserType.PENSKE);
		dao.refreshUserWithSSOData(user);
	}
	
	@Test
	public void shoulddeleteInitialsImage()
	{
		dao.deleteInitialsImage(4354, "600555555");
	}
	
	@Test
	public void shoulddeleteSignatureImage()
	{
		dao.deleteSignatureImage(65467, "600555555");
	}
	
	@Test
	public void shouldgetPenskeUserOrgList()
	{
		dao.getPenskeUserOrgList();
	}
	
	@Test
	public void shouldaddOrg()
	{
		dao.addOrg(makeOrg(2, "TEST", 1));
	}
	
	@Test
	public void shouldgetEditOrgInfo()
	{
		dao.getEditOrgInfo(123);
	}
	
	@Test
	public void shouldupdateOrg()
	{
		dao.updateOrg(makeOrg(2, "TEST", 1));
	}
	
	@Test
	public void shouldgetVendorList()
	{
		this.setPenskeUser();
		dao.getVendorList("HPTL", "%DAIMLER%", 123);
		dao.getVendorList(null, null, 0);
		
		this.setVendorUser();
		dao.getVendorList("HPTL", "%DAIMLER%", 123);
		dao.getVendorList(null, null, 0);
	}
	
	@Test
	public void shouldaddOrgVendor()
	{
		dao.addOrgVendor(12, "76759");
	}
	
	@Test
	public void shoulddeleteVendorAssoc()
	{
		dao.deleteVendorAssoc(123);
	}
	
	@Test
	public void shouldgetOrgVendor()
	{
		this.setPenskeUser();
		dao.getOrgVendor(123);
		
		this.setVendorUser();
		dao.getOrgVendor(123);
	}
	
	@Test
	public void shouldgetSignatureInitialByUserId()
	{
		dao.getSignatureInitialByUserId(456);
	}
	
	@Test
	public void shouldaddBuddies()
	{
		dao.addBuddies(makeUser(123, "600555555", UserType.PENSKE));
	}
	
	@Test
	public void shouldgetUserDeptsById()
	{
		dao.getUserDeptsById(1);
	}
	
	@Test
	public void shouldupdateBuddies()
	{
		dao.updateBuddies(makeUser(123, "600555555", UserType.PENSKE));
	}
	
	@Test
	public void shouldgetVendorUserTypes()
	{
		dao.getVendorUserTypes();
	}
	
	@Test
	public void shoulddeleteUserFromBuddy()
	{
		dao.deleteUserFromBuddy("600555555");
	}
	
	@Test
	public void shoulddeleteOrgVendor()
	{
		dao.deleteOrgVendor(123, new String[]{"567", "8967"});
	}

	//***** HELPER METHODS *****//
	private EditableUser makeUser(int userId, String sso, UserType userType)
	{
		AdminConsoleUserType type = new AdminConsoleUserType();
		type.setUserTypeId(userType.getTypeId());
		
		AdminConsoleUserDept dept = new AdminConsoleUserDept();
		dept.setUserDeptId(1);
		dept.setUserDept("PLANNING");
		
		EditableUser user = new EditableUser();
		user.setUserId(userId);
		user.setSsoId(sso);
		user.setGessouid("aadfas5465");
		user.setEmail("test@penske.com");
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setPhone("1234");
		user.setUserType(type);
		user.setRole(new Role());
		user.setUserDept(dept);
		user.setOrgId(2);
		user.setModifiedBy("600555555");
		user.setCreatedBy("600555555");
		user.setDailyOptIn(false);
		
		return user;
	}
	
	private Org makeOrg(int orgId, String name, int parentOrgId)
	{
		Org org = new Org();
		org.setOrgId(orgId);
		org.setOrgName(name);
		org.setOrgDescription(name);
		org.setParentOrgId(parentOrgId);
		org.setCreatedBy("600555555");
		org.setModifiedBy("600555555");
		return org;
	}
}
