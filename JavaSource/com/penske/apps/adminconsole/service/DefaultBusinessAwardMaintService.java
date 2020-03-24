package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.model.OEM;

@Service
public class DefaultBusinessAwardMaintService implements BusinessAwardMaintService {
	static List<OEM> oemList=new ArrayList<OEM>();
	
	@Override
	public List<OEM> getAllOEMs()
	{
		List<OEM> oemList=getAllOEMsMockService();
		return oemList;
	}
	
	@Override
	public List<String> getAllPoCategory()
	{
		List<String> poCategoryList=getAllPoCategoryMockService();
		return poCategoryList;
	}

	@Override
	public List<String> getAllOEMNames()
	{
		List<String> oemList=getAllOEMNamesMockService();
		return oemList;
	}
	// Mock service methods
	
	public List<OEM> getAllOEMsMockService()
	{
		oemList.clear();
		OEM oem1=new OEM(1, "Chassis","FTL",60);
		OEM oem2=new OEM(2, "Chassis","HIN",30);
		OEM oem3=new OEM(3, "Chassis","IHC",30);
		OEM oem4=new OEM(4, "Body","AMH",5);
		OEM oem5=new OEM(5, "Body","GDT",5);
		OEM oem6=new OEM(6, "Body","KID",5);
		OEM oem7=new OEM(7, "Body","MKY",5);
		OEM oem8=new OEM(8, "Body","MOR",40);
		oemList.add(oem1);
		oemList.add(oem2);
		oemList.add(oem3);
		oemList.add(oem4);
		oemList.add(oem5);
		oemList.add(oem6);
		oemList.add(oem7);
		oemList.add(oem8);
		return oemList;
	}
	
	public List<String> getAllPoCategoryMockService()
	{
		return new ArrayList<String>(Arrays.asList("Truck","Chassis","Body"));
	}
	
	public List<String> getAllOEMNamesMockService()
	{
		return new ArrayList<String>(Arrays.asList("AB - AMERITRANS BUS CO","AC - ARCTIC CAT","ACC - AMERICAN COLEMAN"));
	}
}
