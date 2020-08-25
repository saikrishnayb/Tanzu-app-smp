package com.penske.apps.buildmatrix.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlot;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotRegionAvailability;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;
import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;

public class InvalidSlotsSummary {

	private List<RegionPlantAssociation> regionPlantAssociations;
	private BuildMatrixBodyPlant bodyPlant;
	private List<InvalidSlotsSummaryRow> rows;
	private BuildMatrixSlotType slotType;
	
	public InvalidSlotsSummary(BuildMatrixBodyPlant bodyPlant, BuildMatrixSlotType slotType, List<BuildMatrixSlot> invalidSlots,
			List<BuildMatrixSlotRegionAvailability> invalidRegionSlots, List<BuildMatrixSlotDate> slotDates, List<RegionPlantAssociation> regionPlantAssociations) {
		
		this.regionPlantAssociations = regionPlantAssociations;
		this.bodyPlant = bodyPlant;
		this.slotType = slotType;
		
		Map<Integer, List<BuildMatrixSlotRegionAvailability>> regionSlotsBySlotId = new HashMap<>();
		for(BuildMatrixSlotRegionAvailability rs: invalidRegionSlots) {
			List<BuildMatrixSlotRegionAvailability> rsList = regionSlotsBySlotId.computeIfAbsent(rs.getSlotId(), list -> new ArrayList<>());
			rsList.add(rs);
		}
		
		Map<Integer, BuildMatrixSlot> invalidSlotsByDateId = new HashMap<>();
		Map<Integer, Map<String, BuildMatrixSlotRegionAvailability>> regionSlotsByRegionBySlotId = new HashMap<>();
		for(BuildMatrixSlot slot: invalidSlots) {
			invalidSlotsByDateId.put(slot.getSlotDateId(), slot);
			List<BuildMatrixSlotRegionAvailability> regionSlots = regionSlotsBySlotId.get(slot.getSlotId());
			Map<String, BuildMatrixSlotRegionAvailability> regionSlotsByRegion = new HashMap<>();
			for(BuildMatrixSlotRegionAvailability rs: regionSlots) {
				regionSlotsByRegion.put(rs.getRegion(), rs);
			}
			regionSlotsByRegionBySlotId.put(slot.getSlotId(), regionSlotsByRegion);
		}
		
		if(slotDates.isEmpty() || slotDates==null || invalidSlots.isEmpty() || invalidSlots == null)
			this.rows = Collections.emptyList();
		else {
			List<InvalidSlotsSummaryRow> rows = new ArrayList<>();
			for(BuildMatrixSlotDate slotDate: slotDates) {
				List<InvalidSlotsSummaryCell> cells = new ArrayList<>();
				BuildMatrixSlot slot = invalidSlotsByDateId.get(slotDate.getSlotDateId());
				for(RegionPlantAssociation rpa: regionPlantAssociations) {
					BuildMatrixSlotRegionAvailability regionAvailability = regionSlotsByRegionBySlotId.get(slot.getSlotId()).get(rpa.getRegion());
					cells.add(new InvalidSlotsSummaryCell(bodyPlant, slot, regionAvailability, rpa));
				}
				rows.add(new InvalidSlotsSummaryRow(slotDate, cells));
			}
			this.rows = rows;
		}
	}
	
	public List<RegionPlantAssociation> getRegionPlantAssociations() {
		return regionPlantAssociations;
	}
	
	public List<InvalidSlotsSummaryRow> getRows() {
		return rows;
	}
	
	public BuildMatrixBodyPlant getBodyPlant() {
		return bodyPlant;
	}
	
	public BuildMatrixSlotType getSlotType() {
		return slotType;
	}

	public static class InvalidSlotsSummaryRow {
		private BuildMatrixSlotDate slotDate;
		private List<InvalidSlotsSummaryCell> cells;
		
		public InvalidSlotsSummaryRow(BuildMatrixSlotDate slotDate, 
				List<InvalidSlotsSummaryCell> cells) {
			this.cells = cells;
			this.slotDate = slotDate;
		}
		
		public List<InvalidSlotsSummaryCell> getCells() {
			return cells;
		}
		public BuildMatrixSlotDate getSlotDate() {
			return slotDate;
		}
	}
	
	public static class InvalidSlotsSummaryCell {
		private BuildMatrixBodyPlant bodyPlant;
		private BuildMatrixSlot slot;
		private BuildMatrixSlotRegionAvailability regionAvailability;
		private RegionPlantAssociation regionPlantAssociation;
		
		public InvalidSlotsSummaryCell(BuildMatrixBodyPlant bodyPlant, BuildMatrixSlot slot, 
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
