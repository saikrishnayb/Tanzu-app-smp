package com.penske.apps.buildmatrix.model;


import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlot;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotRegionAvailability;

public class ProductionSlotsUtilizationSummary {
	
	private List<ProductionSlotsUtilizationRow> rows;
	private Map<Integer, BuildMatrixBodyPlant> bodyPlantById;
	private Map<Integer, BuildMatrixSlot> slotById;
	
	public ProductionSlotsUtilizationSummary(List<BuildMatrixBodyPlant> bodyPlantList, 
			List<BuildMatrixSlotDate> slotDates, 
			List<BuildMatrixSlotRegionAvailability> regionAvailabilityList, 
			List<BuildMatrixSlot> slots,
			boolean importModal) {
		
		Map<Integer, BuildMatrixBodyPlant> bodyPlantById = bodyPlantList.stream().collect(toMap(BuildMatrixBodyPlant::getPlantId, bmbp -> bmbp));
		this.bodyPlantById = bodyPlantById;
		
		Map<Integer, BuildMatrixSlot> slotById = slots.stream().collect(toMap(BuildMatrixSlot::getSlotId, sl -> sl));
		this.slotById = slotById;
		
		if(slotDates.isEmpty() || slotDates==null || slots.isEmpty() || slots == null)
			this.rows = Collections.emptyList();
		else {
			Map<Integer, List<BuildMatrixSlot>> slotsByDateId = new HashMap<>();
			for(BuildMatrixSlot slot: slots) {
				List<BuildMatrixSlot> list = slotsByDateId.computeIfAbsent(slot.getSlotDateId(), l-> new ArrayList<>());
				list.add(slot);
			}
			
			Map<Integer, BuildMatrixSlotRegionAvailability> regionAvilabilityBySlotId = new HashMap<>();
			for(BuildMatrixSlotRegionAvailability regionAv: regionAvailabilityList) {
				regionAvilabilityBySlotId.put(regionAv.getSlotId(), regionAv);
			}
			
			List<ProductionSlotsUtilizationRow> rows = new ArrayList<>();
			for(BuildMatrixSlotDate slotDate: slotDates) {
				List<ProductionSlotsUtilizationCell> cells = new ArrayList<>();
				List<BuildMatrixSlot> slotsForDate = slotsByDateId.get(slotDate.getSlotDateId());
				if(slotsForDate == null || slotsForDate.isEmpty()) {
					if(!importModal)
						throw new IllegalStateException("Slots not created for Date: " + slotDate.getFormattedSlotDate());
					else
						continue;
				}
				for(BuildMatrixSlot slot: slotsForDate) {
					BuildMatrixBodyPlant bodyPlant = bodyPlantById.get(slot.getPlantId());
					BuildMatrixSlotRegionAvailability regionAvailability = regionAvilabilityBySlotId.get(slot.getSlotId());
					cells.add(new ProductionSlotsUtilizationCell(bodyPlant, slot, regionAvailability));
				}
				
				rows.add(new ProductionSlotsUtilizationRow(slotDate, cells));
			}
			this.rows = rows;
		}
	}
	
	public Map<Integer, BuildMatrixBodyPlant> getBodyPlantById() {
		return bodyPlantById;
	}
	
	public List<ProductionSlotsUtilizationRow> getRows() {
		return rows;
	}
	
	public Map<Integer, BuildMatrixSlot> getSlotById() {
		return slotById;
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
		
		public ProductionSlotsUtilizationCell(BuildMatrixBodyPlant bodyPlant, BuildMatrixSlot slot, 
				BuildMatrixSlotRegionAvailability regionAvailability) {
			this.bodyPlant = bodyPlant;
			this.slot = slot;
			this.regionAvailability = regionAvailability;
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
	}
	
}
