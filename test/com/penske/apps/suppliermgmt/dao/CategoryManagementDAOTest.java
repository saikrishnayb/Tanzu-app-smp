/**
 * @author john.shiffler (600139252)
 */
package com.penske.apps.suppliermgmt.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.penske.apps.adminconsole.dao.CategoryManagementDao;
import com.penske.apps.adminconsole.model.CategoryAssociation;
import com.penske.apps.smccore.base.configuration.ProfileType;
import com.penske.apps.suppliermgmt.MyBatisDaoTest;
import com.penske.apps.suppliermgmt.TestData;
import com.penske.apps.suppliermgmt.configuration.ApplicationConfiguration;
import com.penske.apps.suppliermgmt.configuration.EmbeddedDataSourceConfiguration;

/**
 * Class under test: {@link CategoryManagementDAO}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@SqlGroup({
    @Sql(scripts = "/setup/create-smc-database.sql"),
    @Sql(scripts = "/setup/drop-smc-schema.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
    @Sql(scripts = "/setup/create-corp-database.sql"),
    @Sql(scripts = "/setup/drop-corp-schema.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
}) 
@ActiveProfiles(ProfileType.TEST)
@Transactional
public class CategoryManagementDAOTest extends MyBatisDaoTest
{
	private final TestData data = new TestData();
	
	@Autowired
	private CategoryManagementDao dao;
	
	@Before
	public void setup()
	{
		dao = trackMethodCalls(dao, CategoryManagementDao.class);
	}
	
	@Test
	public void shouldGetAllSubCategories()
	{
		dao.getAllSubCategories();
	}
	
	@Test
	public void shouldSelectedSubCategory()
	{
		dao.getSelectedSubCategory(12);
	}
	
	@Test
	public void shouldUpdateSubCategory()
	{
		dao.updateSubCategory(data.subcategoryNone);
	}
	
	@Test
	public void shouldInsertSubCategory()
	{
		dao.insertSubCategory(data.subcategoryNone);
	}
	
	@Test
	public void shouldGetAllCategoryAssociation()
	{
		dao.getAllCategoryAssociation();
	}
	
	@Test
	public void shouldGetSubCategories()
	{
		dao.getSubCategories(1);
	}
	
	@Test
	public void shouldAddCategoryAssociation()
	{
		CategoryAssociation assoc = new CategoryAssociation();
		assoc.setPoCategoryId(1);
		assoc.setSubCategoryId(2);
		assoc.setVehicleSizeRequired(true);
		assoc.setVehicleTypeRequired(false);
		assoc.setMakeModelYearRequired(true);
		
		dao.addCategoryAssociation(assoc);
	}
	
	@Test
	public void shouldGetMaxCategoryId()
	{
		dao.getMaxCategoryId();
	}
	
	@Test
	public void shouldGetMaxSubcategoryId()
	{
		dao.getMaxSubCategoryId();
	}
	
	@Test
	public void shouldModifySubCatStatus()
	{
		dao.modifySubCatStatus(2, "A");
	}
	
	@Test
	public void shouldModifySubCatAssocStatus()
	{
		dao.modifySubCatAssocStatus(2, "A");
	}
	
	@Test
	public void shouldModifyAssociationStatus()
	{
		dao.modifyAssociationStatus(5, "A");
	}

	@Test
	public void shouldGetAnyOtherAssociationExist()
	{
		dao.getAnyOtherAssociationExist(1, 2, 5);
	}
	
	@Test
	public void shouldGetSubCategoryByName()
	{
		dao.getSubCategoryByName(data.subcategoryNone);
	}
	
	@Test
	public void shouldGetEditCategoryAssociation()
	{
		dao.getEditCategoryAssociation(2);
	}
	
	@Test
	public void shouldUpdateCategoryAssociation()
	{
		CategoryAssociation assoc = new CategoryAssociation();
		assoc.setAssociationId(6);
		assoc.setPoCategoryId(1);
		assoc.setSubCategoryId(2);
		assoc.setVehicleSizeRequired(true);
		assoc.setVehicleTypeRequired(false);
		assoc.setMakeModelYearRequired(true);
		
		dao.updateCategoryAssociation(assoc);
	}
}
