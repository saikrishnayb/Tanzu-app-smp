package com.penske.apps.adminconsole.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.ComponentSequence;
import com.penske.apps.adminconsole.model.CorpCode;
import com.penske.apps.adminconsole.model.Manufacture;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.SubCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.adminconsole.model.TemplateComponents;
import com.penske.apps.adminconsole.model.TemplatePoCategorySubCategory;
import com.penske.apps.adminconsole.model.VendorLocation;
import com.penske.apps.adminconsole.model.VendorTemplate;
import com.penske.apps.adminconsole.model.VendorTemplateSearch;
/**
 * 
 * @author 600144005
 * This interface is used to get queries defined in component-template-mapper for templates page
 */


public interface ComponentVendorTemplateDao {
	
	public List<VendorTemplate> getVendorTemplates();
	public List<VendorTemplate> getVendorCategories(@Param("vendorNumber") int vendorNumber,@Param("corpCode") String corpCode);
	
	public List<VendorTemplate> selectVenderBySearchCriteria(VendorTemplateSearch template);
	public List<Integer> getVendorNumberByMfr(@Param("MFR") String mfr);
	//public List<VendorTemplate> getVendorTemplateSelectedCorpCode(@Param("MFR") String mfr,@Param("corpCode")String corpCode);
	
	public VendorTemplate getVendorTemplate(int templateId);
	public List<TemplatePoCategorySubCategory> getTemplatePoCategorySubCategory();
	public List<TemplatePoCategorySubCategory> getSearchTemplatePoCategorySubCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	
	public List<TemplateComponents>getTemplateComponents(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId,@Param("templateId")int templateId);
	
	public List<Manufacture> getManufacture();
	public List<CorpCode> getCorpCodes(@Param("MFR") String mfr);
	
	public TemplatePoCategorySubCategory getDeleteModalContent(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	public void  deleteCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId,@Param("templateId")int templateId);
	
	public List<VendorLocation> getVendorLocation(@Param("manufacture") String manufacture,@Param("corpCode") String corpCode);
	
	public List<PoCategory> getPoCategories();
	
	public List<SubCategory> getSubCategories(@Param("poCategoryId")int poCategoryId);
	
	public TemplatePoCategorySubCategory getPoCategorySubCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	public List<TemplateComponents> getCategoryComponents(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	public void addTemplate(@Param("vendorNumber") int vendorNumber,@Param("createdBy") String createdBy);
	
	public VendorTemplate getTemplateId(@Param("vendorNumber") int vendorNumber,@Param("corpCode") String corpCode);
	
	public void addTemplateComponents(@Param("templateComponents")TemplateComponents templateComponents,@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);
	
	public void updateTemplateComponents(TemplateComponents templateComponents);
	

	public List<String> getAllTemplateManufactures();
	
	public TemplateComponents getComponentName(int componentId);
	
	public TemplateComponents getVehicleComponentName(int componentId);
	
	public void deleteTemplateComponents(@Param("templateId")int templateId);
	
	public void deleteTemplate(@Param("templateId")int templateId);
	
	
	public List<PoCategory> getTemplatePoCategory();
	
	public TemplatePoCategorySubCategory getDeleteInEditModalContent(@Param("templateId")int templateId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryIdy);
	
	public int getTemplateComponentCount(@Param("templateId")int templateId);
	
	//Excele Sequence 
	public List<Template> getExcelSeqTemplates();
	public List<ComponentSequence> getTemplateComponentSequences(@Param("templateId") int templateId);
	public void updateTemplateComponentSequence(@Param("templateId") int templateId,@Param("componentId")String componentId, @Param("componentSequence")int componentSequence);
}
