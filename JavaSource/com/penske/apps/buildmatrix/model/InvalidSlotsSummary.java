package com.penske.apps.buildmatrix.model;

import java.util.List;
import java.util.Map;

import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlot;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotRegionAvailability;
import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;

public class InvalidSlotsSummary {

	public InvalidSlotsSummary(Map<Integer, BuildMatrixBodyPlant> bodyPlantsById,
			Map<Integer, List<RegionPlantAssociation>> regionPlantAssociationsByPlantId,
			Map<String, String> regionDescByRegion, Map<Integer, BuildMatrixSlot> invalidSlotsForMfrById,
			Map<Integer, BuildMatrixSlotDate> slotDatesById,
			Map<Integer, List<BuildMatrixSlotRegionAvailability>> invalidRegionSlotsBySlotId) {
		
		
		
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
