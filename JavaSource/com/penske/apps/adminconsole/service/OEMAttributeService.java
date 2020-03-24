package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.BuildMatrixAttribute;

public interface OEMAttributeService {

	public List<BuildMatrixAttribute> getAllBuildMatrixAttributes();

	public BuildMatrixAttribute getAttributeDetails(int attributeId);

	public List<String> getDropdownOptionGrpList();

	public List<String> getDropdownAttrValueList();
	
	public void updateAttribute(BuildMatrixAttribute attributeData);
	
	public void addAttribute(int attributeId, String attributeValue);
	
}
