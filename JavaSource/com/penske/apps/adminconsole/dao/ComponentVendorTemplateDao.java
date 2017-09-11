package com.penske.apps.adminconsole.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.penske.apps.adminconsole.annotation.NonVendorQuery;
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
	
	@NonVendorQuery //TODO: Review Query
	public List<VendorTemplate> getVendorTemplates();
	
	@NonVendorQuery //TODO: Review Query
	public List<VendorTemplate> getVendorCategories(@Param("vendorNumber") int vendorNumber,@Param("corpCode") String corpCode);
	
	@NonVendorQuery //TODO: Review Query
	public List<VendorTemplate> selectVenderBySearchCriteria(VendorTemplateSearch template);
	
	@NonVendorQuery //TODO: Review Query
	public List<Integer> getVendorNumberByMfr(@Param("MFR") String mfr);
	//public List<VendorTemplate> getVendorTemplateSelectedCorpCode(@Param("MFR") String mfr,@Param("corpCode")String corpCode);
	
	@NonVendorQuery //TODO: Review Query
	public VendorTemplate getVendorTemplate(int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public List<TemplatePoCategorySubCategory> getTemplatePoCategorySubCategory();
	
	@NonVendorQuery //TODO: Review Query
	public List<TemplatePoCategorySubCategory> getSearchTemplatePoCategorySubCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public List<TemplateComponents>getTemplateComponents(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId,@Param("templateId")int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public List<Manufacture> getManufacture();
	
	@NonVendorQuery //TODO: Review Query
	public List<CorpCode> getCorpCodes(@Param("MFR") String mfr);
	
	@NonVendorQuery //TODO: Review Query
	public TemplatePoCategorySubCategory getDeleteModalContent(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public void  deleteCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId,@Param("templateId")int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public List<VendorLocation> getVendorLocation(@Param("manufacture") String manufacture,@Param("corpCode") String corpCode);
	
	@NonVendorQuery //TODO: Review Query
	public List<PoCategory> getPoCategories();
	
	@NonVendorQuery //TODO: Review Query
	public List<SubCategory> getSubCategories(@Param("poCategoryId")int poCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public TemplatePoCategorySubCategory getPoCategorySubCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public List<TemplateComponents> getCategoryComponents(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public void addTemplate(@Param("vendorNumber") int vendorNumber,@Param("createdBy") String createdBy);
	
	@NonVendorQuery //TODO: Review Query
	public VendorTemplate getTemplateId(@Param("vendorNumber") int vendorNumber,@Param("corpCode") String corpCode);
	
	@NonVendorQuery //TODO: Review Query
	public void addTemplateComponents(@Param("templateComponents")TemplateComponents templateComponents,@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateTemplateComponents(TemplateComponents templateComponents);
	
	@NonVendorQuery //TODO: Review Query
	public List<String> getAllTemplateManufactures();
	
	@NonVendorQuery //TODO: Review Query
	public TemplateComponents getComponentName(int componentId);
	
	@NonVendorQuery //TODO: Review Query
	public TemplateComponents getVehicleComponentName(int componentId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteTemplateComponents(@Param("templateId")int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public void deleteTemplate(@Param("templateId")int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public List<PoCategory> getTemplatePoCategory();
	
	@NonVendorQuery //TODO: Review Query
	public TemplatePoCategorySubCategory getDeleteInEditModalContent(@Param("templateId")int templateId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryIdy);
	
	@NonVendorQuery //TODO: Review Query
	public int getTemplateComponentCount(@Param("templateId")int templateId);
	
	//Excele Sequence 
	@NonVendorQuery //TODO: Review Query
	public List<Template> getExcelSeqTemplates();
	
	@NonVendorQuery //TODO: Review Query
	public List<ComponentSequence> getTemplateComponentSequences(@Param("templateId") int templateId);
	
	@NonVendorQuery //TODO: Review Query
	public void updateTemplateComponentSequence(@Param("templateId") int templateId,@Param("componentId")String componentId, @Param("componentSequence")int componentSequence);
}
