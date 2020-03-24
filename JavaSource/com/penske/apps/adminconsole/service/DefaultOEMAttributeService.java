/**
 * 
 */
package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.OEMAttributeDao;
import com.penske.apps.adminconsole.model.BuildMatrixAttribute;

/**
 * @author 505023328
 *
 */

@Service
public class DefaultOEMAttributeService implements OEMAttributeService {
	@Autowired
	private OEMAttributeDao attributeDao;

	static List<BuildMatrixAttribute> buildMatrixAttributeList = new ArrayList<BuildMatrixAttribute>();

	@Override
	public List<BuildMatrixAttribute> getAllBuildMatrixAttributes() 
	{
		List<BuildMatrixAttribute> buildMatrixAttribute = attributeDao.getAllBuildMatrixAttributes();
				//getAllattributesmockDataService();
		return buildMatrixAttribute;
	}

	@Override
	public BuildMatrixAttribute getAttributeDetails(int attributeId) 
	{
		BuildMatrixAttribute buildMatrixAttribute = attributeDao.getAttributeDetails(attributeId);
		return buildMatrixAttribute;
	}

	@Override
	public List<String> getDropdownOptionGrpList() 
	{
		List<String> dropdownOptionGrpList = getMockDropdownOptionGrpList();
		// List<String> dropdownOptionGrpList=attributeDao.getDropdownOptionGrpList();;
		return dropdownOptionGrpList;
	}

	@Override
	public List<String> getDropdownAttrValueList() 
	{
		List<String> dropdownAttrValueList = getMockDropdownAttrValueList();
		//List<String> dropdownAttrValueList = attributeDao.getDropdownAttrValueList();
		return dropdownAttrValueList;
	}

	@Override
	public void updateAttribute(BuildMatrixAttribute attributeData)
	{
		//attributeDao.updateAttribute(attributeData); //need to confirm attribute name update
		attributeDao.updateAttributeValues(attributeData);
	}
	
	@Override
	public void addAttribute(int attributeId, String attributeValue)
	{
		attributeDao.addAttribute(attributeId,attributeValue);
	}
	
	// Mock service methods
	private BuildMatrixAttribute getAttributeMockService(int attributeId) {
		BuildMatrixAttribute resultAttribute = null;
		for (BuildMatrixAttribute attribute : buildMatrixAttributeList) {
			if (attribute.getAttributeId() == attributeId)
				resultAttribute = attribute;
		}
		return resultAttribute;
	}

	private List<BuildMatrixAttribute> getAllattributesmockDataService() {
		buildMatrixAttributeList.clear();
		/*
		BuildMatrixAttribute attribute = new BuildMatrixAttribute(1, "WHEELBASE", "CHASSIS QUALIFICATION", "Truck",new ArrayList<String>(Arrays.asList("248", "251", "254", "260")));
		BuildMatrixAttribute attribute1 = new BuildMatrixAttribute(2, "BODY MAKE", "OEM MIX", "Car",new ArrayList<String>(Arrays.asList("MORGAN", "SUPREME")));
		BuildMatrixAttribute attribute2 = new BuildMatrixAttribute(3, "BODY COLOR", "BODY QUALIFICATION", "Van",new ArrayList<String>(Arrays.asList("WHITE", "YELLOW")));
		buildMatrixAttributeList.add(attribute);
		buildMatrixAttributeList.add(attribute1);
		buildMatrixAttributeList.add(attribute2);
		*/
		return buildMatrixAttributeList;
	}

	public List<String> getMockDropdownOptionGrpList() {
		return new ArrayList<String>(Arrays.asList("Truck", "Car", "Van"));

	}

	public List<String> getMockDropdownAttrValueList() {
		return new ArrayList<String>(Arrays.asList("261", "263", "264", "270"));

	}

}
