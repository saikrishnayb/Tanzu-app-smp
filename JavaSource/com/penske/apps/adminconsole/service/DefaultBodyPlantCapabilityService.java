package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.BodyPlantCapabilityDao;
import com.penske.apps.adminconsole.model.BodyPlantCapability;

@Service
public class DefaultBodyPlantCapabilityService implements BodyPlantCapabilityService {
	@Autowired
	BodyPlantCapabilityDao capabilityDao;
	
	static List<BodyPlantCapability> bodyPlantCapabilityList=new ArrayList<BodyPlantCapability>();
	
	@Override
	public List<BodyPlantCapability> getAllBuildMatrixCapabilities() {
		List<BodyPlantCapability> bodyPlantCapabilityList=getAllCapabilitymockDataService();
		//List<BodyPlantCapability> bodyPlantCapabilityList=capabilityDao.getAllBuildMatrixCapabilities();
		
		return bodyPlantCapabilityList;
	}
	
	@Override
	public BodyPlantCapability getCapabilityDetails(int capabilityId) {
		BodyPlantCapability capability=getCapabilityMockService(capabilityId);
		//BodyPlantCapability capability=capabilityDao.getCapabilityDetails(capabilityId);
		return capability;
	}
	
	//Mock service methods
	private List<BodyPlantCapability> getAllCapabilitymockDataService() {
		bodyPlantCapabilityList.clear();
		BodyPlantCapability attribute=new BodyPlantCapability(1,"BODY","Length",new ArrayList<String>(Arrays.asList("16'","18'","20'","26'")),new ArrayList<String>(Arrays.asList("16'","18'","20'")));
		BodyPlantCapability attribute1=new BodyPlantCapability(2,"BODY","Width",new ArrayList<String>(Arrays.asList("96'","102'")),new ArrayList<String>(Arrays.asList("96'")));
		BodyPlantCapability attribute2=new BodyPlantCapability(3,"BODY","Height",new ArrayList<String>(Arrays.asList("91'","97'","103'")),new ArrayList<String>(Arrays.asList("91'","97'")));
		BodyPlantCapability attribute3=new BodyPlantCapability(4,"BODY","Walkramp",new ArrayList<String>(Arrays.asList("YES","NO")),new ArrayList<String>(Arrays.asList("YES","NO")));
		BodyPlantCapability attribute4=new BodyPlantCapability(5,"BODY","Side Door",new ArrayList<String>(Arrays.asList("YES","NO")),new ArrayList<String>(Arrays.asList("YES","NO")));
		bodyPlantCapabilityList.add(attribute);
		bodyPlantCapabilityList.add(attribute1);
		bodyPlantCapabilityList.add(attribute2);
		bodyPlantCapabilityList.add(attribute3);
		bodyPlantCapabilityList.add(attribute4);
		return bodyPlantCapabilityList;
	}
	
	private BodyPlantCapability getCapabilityMockService(int capabilityId) {
		BodyPlantCapability resultCapability=null;
		for(BodyPlantCapability capability:bodyPlantCapabilityList)
		{
			if(capability.getCapabilityId()==capabilityId)
				resultCapability=capability;
		}
		return resultCapability;
	}
}
