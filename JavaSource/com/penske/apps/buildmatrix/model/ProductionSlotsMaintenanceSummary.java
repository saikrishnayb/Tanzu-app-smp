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

public class ProductionSlotsMaintenanceSummary {
	
	private List<ProductionSlotsMaintenanceRow> rows;
	private Map<Integer, BuildMatrixBodyPlant> bodyPlantById;
	
	public ProductionSlotsMaintenanceSummary(List<BuildMatrixBodyPlant> bodyPlantList, 
			List<BuildMatrixSlotDate> slotDates,  
			List<BuildMatrixSlot> slots) {
		
		Map<Integer, BuildMatrixBodyPlant> bodyPlantById = bodyPlantList.stream().collect(toMap(BuildMatrixBodyPlant::getPlantId, bmbp -> bmbp));
		this.bodyPlantById = bodyPlantById;
		
		if(slotDates.isEmpty() || slotDates==null || slots.isEmpty() || slots == null)
			this.rows = Collections.emptyList();
		else {
			Map<Integer, List<BuildMatrixSlot>> slotsByDateId = new HashMap<>();
			for(BuildMatrixSlot slot: slots) {
				List<BuildMatrixSlot> list = slotsByDateId.computeIfAbsent(slot.getSlotDateId(), l-> new ArrayList<>());
				list.add(slot);
			}
			
			List<ProductionSlotsMaintenanceRow> rows = new ArrayList<>();
			for(BuildMatrixSlotDate slotDate: slotDates) {
				List<ProductionSlotsMaintenanceCell> cells = new ArrayList<>();
				for(BuildMatrixSlot slot: slotsByDateId.get(slotDate.getSlotDateId())) {
					BuildMatrixBodyPlant bodyPlant = bodyPlantById.get(slot.getPlantId());
					cells.add(new ProductionSlotsMaintenanceCell(bodyPlant, slot));
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
		
		public ProductionSlotsMaintenanceCell(BuildMatrixBodyPlant bodyPlant, BuildMatrixSlot slot) {
			this.bodyPlant = bodyPlant;
			this.slot = slot;
		}
		
		public BuildMatrixBodyPlant getBodyPlant() {
			return bodyPlant;
		}
		public BuildMatrixSlot getSlot() {
			return slot;
		}
	}
	
}
