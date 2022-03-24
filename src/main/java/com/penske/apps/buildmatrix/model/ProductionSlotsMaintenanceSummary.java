package com.penske.apps.buildmatrix.model;


import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlot;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotDate;

public class ProductionSlotsMaintenanceSummary {
	
	private List<ProductionSlotsMaintenanceRow> rows;
	private Map<Integer, BuildMatrixBodyPlant> bodyPlantById;
	
	public ProductionSlotsMaintenanceSummary(List<BuildMatrixBodyPlant> bodyPlantList, 
			List<BuildMatrixSlotDate> slotDates,  
			List<BuildMatrixSlot> slots) {
		
		Map<Integer, BuildMatrixBodyPlant> bodyPlantById = new HashMap<>();
		Map<Integer, Map<Integer,BuildMatrixSlot>> slotByDateIdByPlantId = new HashMap<>();
		for(BuildMatrixBodyPlant plant: bodyPlantList) {
			int plantId = plant.getPlantId();
			bodyPlantById.put(plantId, plant);
			List<BuildMatrixSlot> slotList = slots.stream()
					.filter(slot -> plantId == slot.getPlantId())
					.collect(toList());
			if(!slotList.isEmpty()) {
				Map<Integer, BuildMatrixSlot> slotBySlotDateId = slotList.stream()
						.collect(toMap(BuildMatrixSlot::getSlotDateId, slot-> slot));
				slotByDateIdByPlantId.put(plantId, slotBySlotDateId);
			}
			else
				slotByDateIdByPlantId.put(plantId, Collections.emptyMap());
		}
		this.bodyPlantById = bodyPlantById;
		
		if(slotDates.isEmpty() || slotDates==null || slots == null)
			this.rows = Collections.emptyList();
		else {
//			Map<Integer, List<BuildMatrixSlot>> slotsByDateId = new HashMap<>();
//			for(BuildMatrixSlot slot: slots) {
//				List<BuildMatrixSlot> list = slotsByDateId.computeIfAbsent(slot.getSlotDateId(), l-> new ArrayList<>());
//				list.add(slot);
//			}
			
			List<ProductionSlotsMaintenanceRow> rows = new ArrayList<>();
			for(BuildMatrixSlotDate slotDate: slotDates) {
				List<ProductionSlotsMaintenanceCell> cells = new ArrayList<>();
				
//				List<BuildMatrixSlot> slotsForDate = slotsByDateId.get(slotDate.getSlotDateId());
//				if(slotsForDate == null || slotsForDate.isEmpty()) {
//					if(!importModal)
//						throw new IllegalStateException("Slots not created for Date: " + slotDate.getFormattedSlotDate());
//					else
//						continue;
//				}
//				for(BuildMatrixSlot slot: slotsForDate) {
//					BuildMatrixBodyPlant bodyPlant = bodyPlantById.get(slot.getPlantId());
//					cells.add(new ProductionSlotsMaintenanceCell(bodyPlant, slot));
//				}
//				for(BuildMatrixBodyPlant plant: plantsWithoutSlots) {
//					cells.add(new ProductionSlotsMaintenanceCell(plant, slotDate.getSlotDateId()));
//				}
				
				for(BuildMatrixBodyPlant plant: bodyPlantList) {
					Map<Integer, BuildMatrixSlot> slotsBySlotDate = slotByDateIdByPlantId.get(plant.getPlantId());
					if(!slotsBySlotDate.isEmpty()) {
						BuildMatrixSlot slot = slotsBySlotDate.get(slotDate.getSlotDateId());
						if(slot != null)
							cells.add(new ProductionSlotsMaintenanceCell(plant, slot));
						else
							cells.add(new ProductionSlotsMaintenanceCell(plant, slotDate.getSlotDateId()));
					}
					else
						cells.add(new ProductionSlotsMaintenanceCell(plant, slotDate.getSlotDateId()));
				}
				
				rows.add(new ProductionSlotsMaintenanceRow(slotDate, cells));
			}
			this.rows = rows;
		}
	}
	
	public Map<Integer, BuildMatrixBodyPlant> getBodyPlantById() {
		return bodyPlantById;
	}
	
	
	public List<ProductionSlotsMaintenanceRow> getRows() {
		return rows;
	}
	
	public static class ProductionSlotsMaintenanceRow {
		private BuildMatrixSlotDate slotDate;
		private List<ProductionSlotsMaintenanceCell> cells;
		
		public ProductionSlotsMaintenanceRow(BuildMatrixSlotDate slotDate, 
				List<ProductionSlotsMaintenanceCell> cells) {
			this.cells = cells;
			this.slotDate = slotDate;
		}
		
		public List<ProductionSlotsMaintenanceCell> getCells() {
			return cells;
		}
		public BuildMatrixSlotDate getSlotDate() {
			return slotDate;
		}
	}
	
	public static class ProductionSlotsMaintenanceCell {
		private BuildMatrixBodyPlant bodyPlant;
		private BuildMatrixSlot slot;
		private int slotDateId;
		
		public ProductionSlotsMaintenanceCell(BuildMatrixBodyPlant bodyPlant, BuildMatrixSlot slot) {
			this.bodyPlant = bodyPlant;
			this.slot = slot;
			this.slotDateId = slot.getSlotDateId();
		}
		
		public ProductionSlotsMaintenanceCell(BuildMatrixBodyPlant bodyPlant, int slotDateId) {
			this.bodyPlant = bodyPlant;
			this.slot = null;
			this.slotDateId = slotDateId;
		}
		
		public BuildMatrixBodyPlant getBodyPlant() {
			return bodyPlant;
		}
		public BuildMatrixSlot getSlot() {
			return slot;
		}
		public Integer getSlotDateId() {
			return slotDateId;
		}
	}
	
}
