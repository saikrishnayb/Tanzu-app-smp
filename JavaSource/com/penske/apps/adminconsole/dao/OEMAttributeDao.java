package com.penske.apps.adminconsole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.model.BuildMatrixAttribute;
import com.penske.apps.smccore.base.annotation.NonVendorQuery;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

@DBSmc
public interface OEMAttributeDao {

	@NonVendorQuery
	public List<String> getDropdownOptionGrpList();

	@NonVendorQuery
	public List<BuildMatrixAttribute> getAllBuildMatrixAttributes();

	@NonVendorQuery
	public BuildMatrixAttribute getAttributeDetails(int attributeId);

	@NonVendorQuery
	public List<String> getDropdownAttrValueList();
	
	@NonVendorQuery
	public void updateAttribute(BuildMatrixAttribute attributeData);
	
	@NonVendorQuery
	public void updateAttributeValues(BuildMatrixAttribute attributeData);
	
	@NonVendorQuery
	public void addAttribute(@Param("attributeId") int attributeId, @Param("attributeValue") String attributeValue);
	
}
