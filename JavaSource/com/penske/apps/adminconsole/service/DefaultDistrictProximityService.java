package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.DistrictProximityDao;
import com.penske.apps.adminconsole.model.DistrictProximity;

@Service
public class DefaultDistrictProximityService implements DistrictProximityService {

	@Autowired
	DistrictProximityDao districtProximityDao;
	
	static List<DistrictProximity> districtProximity = new ArrayList<DistrictProximity>();
	static List<DistrictProximity> districtProximityList = new ArrayList<DistrictProximity>();

	@Override
	public List<DistrictProximity> getDistrictProximity() {
		List<DistrictProximity> districtProximity = districtProximityDao.getDistrictProximity();
		System.out.println("districtProximity.size() ==== "+districtProximity.size());
		return districtProximity;
	}
	
	// mock service methods
	public List<DistrictProximity> getDistrictProximityMockService() {
		
		districtProximityList.clear();
		DistrictProximity dp1 = new DistrictProximity("0602 - MID ATLANTIC",1);
		DistrictProximity dp2 = new DistrictProximity("0603 - NORTHEAST",1);
		DistrictProximity dp3 = new DistrictProximity("0603 - MIDDLE SOUTH",1);
		DistrictProximity dp4 = new DistrictProximity("0607 - FLORIDA",1);
		DistrictProximity dp5 = new DistrictProximity("0608 - MIDWEST",1);
		DistrictProximity dp6 = new DistrictProximity("0609 - SOUTHWEST",1);
		DistrictProximity dp7 = new DistrictProximity("0610 - NORTH WEST",1);
		DistrictProximity dp8 = new DistrictProximity("0611 - METRO NEW YORK",1);
		DistrictProximity dp9 = new DistrictProximity("0612 - CENTRAL",1);
		DistrictProximity dp10 = new DistrictProximity("0614 - SOUTH CENTRAL",1);
		DistrictProximity dp11 = new DistrictProximity("0616 - CAROLINAS",1);
		DistrictProximity dp12 = new DistrictProximity("0617 - NORTH CENTRAL",1);
		DistrictProximity dp13 = new DistrictProximity("0621 - EASTERN CANADA",1);
		DistrictProximity dp14 = new DistrictProximity("0623 - WESTERN CANADA",1);
		DistrictProximity dp15 = new DistrictProximity("0642 - MOUNTAIN AREA",1);
		DistrictProximity dp16 = new DistrictProximity("0644 - GULF STATES AREA",1);
		
		districtProximityList.add(dp1);
		districtProximityList.add(dp2);
		districtProximityList.add(dp3);
		districtProximityList.add(dp4);
		districtProximityList.add(dp5);
		districtProximityList.add(dp6);
		districtProximityList.add(dp7);
		districtProximityList.add(dp8);
		districtProximityList.add(dp9);
		districtProximityList.add(dp10);
		districtProximityList.add(dp11);
		districtProximityList.add(dp12);
		districtProximityList.add(dp13);
		districtProximityList.add(dp14);
		districtProximityList.add(dp15);
		districtProximityList.add(dp16);
		
		return districtProximityList;
	}
	
	@Override
	public void insertProximityValues(DistrictProximity districtProximity) {
		districtProximityDao.insertProximityValues(districtProximity);		
	}

}
