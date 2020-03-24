package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.model.ProductionSlotsMaintenance;

@Service
public class DefaultBodyPlantMaintSummaryService implements BodyPlantMaintSummaryService {
	
	static List<ProductionSlotsMaintenance> maintenanceSummaryList= new ArrayList<ProductionSlotsMaintenance>();
	
	@Override
	public List<ProductionSlotsMaintenance> getMaintenanceSummary() {
		
		List<ProductionSlotsMaintenance> maintenanceSummary= getMaintenanceSummaryMockService();
		return maintenanceSummary;
	}
	
	//mock service methods
	public List<ProductionSlotsMaintenance> getMaintenanceSummaryMockService() {
		maintenanceSummaryList.clear();
		ProductionSlotsMaintenance slot1= new ProductionSlotsMaintenance(1,"MORGAN", "DENVER", "PA", "0834", "0602", null);
		ProductionSlotsMaintenance slot2= new ProductionSlotsMaintenance(1,"MORGAN", "PORTLAND", "OR", "0832", "0602", null);
		ProductionSlotsMaintenance slot3= new ProductionSlotsMaintenance(1,"MORGAN", "DENVER", "CO", "0832", "0602", null);
		ProductionSlotsMaintenance slot4= new ProductionSlotsMaintenance(1,"MORGAN", "EPHRATA", "PA", "0834", "0602", null);
		ProductionSlotsMaintenance slot5= new ProductionSlotsMaintenance(1,"MORGAN", "MORGANTOWN", "PA", "0834", "0602", null);
		ProductionSlotsMaintenance slot6= new ProductionSlotsMaintenance(1,"MORGAN", "RIVERSIDE", "CA", "0832", "0602", null);
		ProductionSlotsMaintenance slot7= new ProductionSlotsMaintenance(1,"MORGAN", "DENVER", "PA", "0834", "0834", null);
		ProductionSlotsMaintenance slot8= new ProductionSlotsMaintenance(1,"MORGAN", "PORTLAND", "OR", "0834", "0832", null);
		ProductionSlotsMaintenance slot9= new ProductionSlotsMaintenance(1,"MORGAN", "EPHRATA", "CO", "0834", "0832", null);
		maintenanceSummaryList.add(slot1);
		maintenanceSummaryList.add(slot2);
		maintenanceSummaryList.add(slot3);
		maintenanceSummaryList.add(slot4);
		maintenanceSummaryList.add(slot5);
		maintenanceSummaryList.add(slot6);
		maintenanceSummaryList.add(slot7);
		maintenanceSummaryList.add(slot8);
		maintenanceSummaryList.add(slot9);
		return maintenanceSummaryList;
	}
}
