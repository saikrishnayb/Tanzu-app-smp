package com.penske.apps.adminconsole.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.ComponentSequence;
import com.penske.apps.adminconsole.model.PoCategory;
import com.penske.apps.adminconsole.model.Template;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;
/**
 * 
 * @author 600144005
 * This interface is used to get queries defined in component-template-mapper for templates page
 */

@DBSmc
public interface ComponentVendorTemplateDao {

    @NonVendorQuery
    public List<PoCategory> getPoCategories();

    //Excele Sequence
    @NonVendorQuery
    public List<Template> getExcelSeqTemplates(@Param("templateId") Integer templateId);

    @NonVendorQuery
    public List<ComponentSequence> getTemplateComponentSequences(@Param("templateId") int templateId);

    @NonVendorQuery
    public void updateTemplateComponentSequence(@Param("templateId") int templateId,@Param("componentId")String componentId, @Param("componentSequence")int componentSequence);
}
