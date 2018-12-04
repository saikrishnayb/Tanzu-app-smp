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
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
/**
 * 
 * @author 600144005
 * This interface is used to get queries defined in component-template-mapper for templates page
 */


public interface ComponentVendorTemplateDao {

    public List<VendorTemplate> getVendorTemplates();

    public List<VendorTemplate> getVendorCategories(@Param("vendorNumber") int vendorNumber,@Param("corpCode") String corpCode);

    public VendorTemplate getVendorTemplate(int templateId);

    public List<Manufacture> getManufacture();

    @NonVendorQuery
    public List<CorpCode> getCorpCodes(@Param("MFR") String mfr);

    public List<VendorTemplate> selectVenderBySearchCriteria(VendorTemplateSearch template);

    public List<Integer> getVendorNumberByMfr(@Param("MFR") String mfr);

    @NonVendorQuery
    public List<TemplatePoCategorySubCategory> getTemplatePoCategorySubCategory();

    @NonVendorQuery
    public List<TemplatePoCategorySubCategory> getSearchTemplatePoCategorySubCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public List<TemplateComponents>getTemplateComponents(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId,@Param("templateId")int templateId);



    @NonVendorQuery
    public TemplatePoCategorySubCategory getDeleteModalContent(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public void  deleteCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId,@Param("templateId")int templateId);

    public List<VendorLocation> getVendorLocation(@Param("manufacture") String manufacture,@Param("corpCode") String corpCode);

    @NonVendorQuery
    public List<PoCategory> getPoCategories();

    @NonVendorQuery
    public List<SubCategory> getSubCategories(@Param("poCategoryId")int poCategoryId);

    @NonVendorQuery
    public TemplatePoCategorySubCategory getPoCategorySubCategory(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    @NonVendorQuery
    public List<TemplateComponents> getCategoryComponents(@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryId);

    public void addTemplate(@Param("vendorNumber") int vendorNumber,@Param("createdBy") String createdBy);

    public VendorTemplate getTemplateId(@Param("vendorNumber") int vendorNumber,@Param("corpCode") String corpCode);

    @NonVendorQuery
    public void addTemplateComponents(@Param("templateComponents")TemplateComponents templateComponents,@Param("poCategoryId") int poCategoryId,@Param("subCategoryId") int subCategoryId);

    @NonVendorQuery
    public void updateTemplateComponents(TemplateComponents templateComponents);

    public List<String> getAllTemplateManufactures();

    @NonVendorQuery
    public TemplateComponents getComponentName(int componentId);

    @NonVendorQuery // TODO: Review Query
    public TemplateComponents getVehicleComponentName(int componentId);

    @NonVendorQuery
    public void deleteTemplateComponents(@Param("templateId")int templateId);

    @NonVendorQuery
    public void deleteTemplate(@Param("templateId")int templateId);

    @NonVendorQuery
    public List<PoCategory> getTemplatePoCategory();

    @NonVendorQuery
    public TemplatePoCategorySubCategory getDeleteInEditModalContent(@Param("templateId")int templateId,@Param("poCategoryId")int poCategoryId,@Param("subCategoryId")int subCategoryIdy);

    @NonVendorQuery
    public int getTemplateComponentCount(@Param("templateId")int templateId);

    //Excele Sequence
    @NonVendorQuery
    public List<Template> getExcelSeqTemplates(@Param("templateId") Integer templateId);

    @NonVendorQuery
    public List<ComponentSequence> getTemplateComponentSequences(@Param("templateId") int templateId);

    @NonVendorQuery
    public void updateTemplateComponentSequence(@Param("templateId") int templateId,@Param("componentId")String componentId, @Param("componentSequence")int componentSequence);
}
