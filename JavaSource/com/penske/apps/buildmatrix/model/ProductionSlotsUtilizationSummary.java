package com.penske.apps.buildmatrix.model;


import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlot;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotRegionAvailability;
import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;
import com.penske.apps.smccore.base.util.DateUtil;

public class ProductionSlotsUtilizationSummary {
	
	private List<ProductionSlotsUtilizationRow> rows;
	Map<String, List<RegionPlantAssociation>> plantAssociationsByRegion;
	Map<Integer, BuildMatrixBodyPlant> bodyPlantById;
	
	public ProductionSlotsUtilizationSummary(List<RegionPlantAssociation> regionPlantAsscoiationList, 
			List<BuildMatrixBodyPlant> bodyPlantList, 
			List<BuildMatrixSlotDate> slotDates, 
			List<BuildMatrixSlotRegionAvailability> regionAvailabilityList) {
		
		Map<String, List<RegionPlantAssociation>> plantAssociationsByRegion = new HashMap<>();
		for(RegionPlantAssociation rpa: regionPlantAsscoiationList) {
			List<RegionPlantAssociation> regionList = plantAssociationsByRegion.computeIfAbsent(rpa.getRegion(), list->new ArrayList<>());
			regionList.add(rpa);
		}
		this.plantAssociationsByRegion = plantAssociationsByRegion;
		
		Map<Integer, BuildMatrixBodyPlant> bodyPlantById = bodyPlantList.stream().collect(toMap(BuildMatrixBodyPlant::getPlantId, bmbp -> bmbp));
		this.bodyPlantById = bodyPlantById;
		
		Map<Pair<Integer, Integer>, BuildMatrixSlot> slotsByDateIdandPlantId = new HashMap<>();
		for(BuildMatrixSlotDate slotDate: slotDates) {
			for(BuildMatrixSlot slot: slotDate.getBuildSlots()) {
				slotsByDateIdandPlantId.put(Pair.of(slotDate.getSlotDateId(), slot.getPlantId()), slot);
			}
		}
		
		Map<Pair<String, Integer>, BuildMatrixSlotRegionAvailability> regionAvilabilityByRegionAndSlotId = new HashMap<>();
		for(BuildMatrixSlotRegionAvailability regionAv: regionAvailabilityList) {
			regionAvilabilityByRegionAndSlotId.put(Pair.of(regionAv.getRegion(),regionAv.getSlotId()), regionAv);
		}
		
		List<ProductionSlotsUtilizationRow> rows = new ArrayList<>();
		for(BuildMatrixSlotDate slotDate: slotDates) {
			List<ProductionSlotsUtilizationCell> cells = new ArrayList<>();
			for(Entry<String, List<RegionPlantAssociation>> entry: plantAssociationsByRegion.entrySet()) {
				for(RegionPlantAssociation regionPlant:entry.getValue()) {
					BuildMatrixSlot slot = slotsByDateIdandPlantId.get(Pair.of(slotDate.getSlotDateId(), regionPlant.getPlantId()));
					BuildMatrixBodyPlant bodyPlant = bodyPlantById.get(regionPlant.getPlantId());
					BuildMatrixSlotRegionAvailability regionAvailability = regionAvilabilityByRegionAndSlotId.get(Pair.of(entry.getKey(), slot.getSlotId()));
					
					cells.add(new ProductionSlotsUtilizationCell(bodyPlant, slot, regionAvailability, regionPlant));
				}
			}
			
			rows.add(new ProductionSlotsUtilizationRow(slotDate, cells));
		}
		this.rows = rows;
	}
	
	public Map<Integer, BuildMatrixBodyPlant> getBodyPlantById() {
		return bodyPlantById;
	}
	
	public Map<String, List<RegionPlantAssociation>> getPlantAssociationsByRegion() {
		return plantAssociationsByRegion;
	}
	
	public List<ProductionSlotsUtilizationRow> getRows() {
		return rows;
	}
	
	public static class ProductionSlotsUtilizationRow {
		private BuildMatrixSlotDate slotDate;
		private List<ProductionSlotsUtilizationCell> cells;
		
		public ProductionSlotsUtilizationRow(BuildMatrixSlotDate slotDate, 
				List<ProductionSlotsUtilizationCell> cells) {
			this.cells = cells;
			this.slotDate = slotDate;
		}
		
		public List<ProductionSlotsUtilizationCell> getCells() {
			return cells;
		}
		public BuildMatrixSlotDate getSlotDate() {
			return slotDate;
		}
	}
	
	public static class ProductionSlotsUtilizationCell {
		private BuildMatrixBodyPlant bodyPlant;
		private BuildMatrixSlot slot;
		private BuildMatrixSlotRegionAvailability regionAvailability;
		private RegionPlantAssociation regionPlantAssociation;
		
		public ProductionSlotsUtilizationCell(BuildMatrixBodyPlant bodyPlant, BuildMatrixSlot slot, 
				BuildMatrixSlotRegionAvailability regionAvailability, RegionPlantAssociation regionPlantAssociation) {
			this.bodyPlant = bodyPlant;
			this.slot = slot;
			this.regionAvailability = regionAvailability;
			this.regionPlantAssociation = regionPlantAssociation;
		}
		
		public BuildMatrixBodyPlant getBodyPlant() {
			return bodyPlant;
		}
		public BuildMatrixSlotRegionAvailability getRegionAvailability() {
			return regionAvailability;
		}
		public BuildMatrixSlot getSlot() {
			return slot;
		}
		public RegionPlantAssociation getRegionPlantAssociation() {
			return regionPlantAssociation;
		}
	}
	
}
