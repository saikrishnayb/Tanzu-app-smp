package com.penske.apps.buildmatrix.model;


import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

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
		
		Map<Integer, BuildMatrixBodyPlant> bodyPlantById = new HashMap<>();
		Map<Integer, Map<Integer, Pair<BuildMatrixSlot, BuildMatrixSlotRegionAvailability>>> slotAndRegionSlotByDateIdByPlantId = new HashMap<>();
		for(BuildMatrixBodyPlant plant: bodyPlantList) {
			int plantId = plant.getPlantId();
			bodyPlantById.put(plantId, plant);
			List<BuildMatrixSlot> slotList = slots.stream()
					.filter(slot -> plantId == slot.getPlantId())
					.collect(toList());
			List<Integer> slotIds = slotList
					.stream()
					.map(sl -> sl.getSlotId())
					.collect(toList());
			List<BuildMatrixSlotRegionAvailability> regionAvailabilities = regionAvailabilityList.stream()
					.filter(ra -> slotIds.contains(ra.getSlotId()))
					.collect(toList());
			Map<Integer, BuildMatrixSlotRegionAvailability> regionAvailabilitiesBySlotId = regionAvailabilities.stream()
					.collect(toMap(BuildMatrixSlotRegionAvailability::getSlotId, ra -> ra));
			if(!slotList.isEmpty()) {
				Map<Integer, Pair<BuildMatrixSlot, BuildMatrixSlotRegionAvailability>> slotAndRegionSlotBySlotDateId = slotList.stream()
						.collect(toMap(BuildMatrixSlot::getSlotDateId, slot-> Pair.of(slot, regionAvailabilitiesBySlotId.get(slot.getSlotId()))));
				slotAndRegionSlotByDateIdByPlantId.put(plantId, slotAndRegionSlotBySlotDateId);
			}
			else
				slotAndRegionSlotByDateIdByPlantId.put(plantId, Collections.emptyMap());
		}
		this.bodyPlantById = bodyPlantById;
		
		Map<Integer, BuildMatrixSlot> slotById = slots.stream().collect(toMap(BuildMatrixSlot::getSlotId, sl -> sl));
		this.slotById = slotById;
		
		if(slotDates.isEmpty() || slotDates==null || slots.isEmpty() || slots == null)
			this.rows = Collections.emptyList();
		else {
//			Map<Integer, List<BuildMatrixSlot>> slotsByDateId = new HashMap<>();
//			for(BuildMatrixSlot slot: slots) {
//				List<BuildMatrixSlot> list = slotsByDateId.computeIfAbsent(slot.getSlotDateId(), l-> new ArrayList<>());
//				list.add(slot);
//			}
			
			Map<Integer, BuildMatrixSlotRegionAvailability> regionAvilabilityBySlotId = new HashMap<>();
			for(BuildMatrixSlotRegionAvailability regionAv: regionAvailabilityList) {
				regionAvilabilityBySlotId.put(regionAv.getSlotId(), regionAv);
			}
			
			List<ProductionSlotsUtilizationRow> rows = new ArrayList<>();
			for(BuildMatrixSlotDate slotDate: slotDates) {
				List<ProductionSlotsUtilizationCell> cells = new ArrayList<>();
//				List<BuildMatrixSlot> slotsForDate = slotsByDateId.get(slotDate.getSlotDateId());
//				if(slotsForDate == null || slotsForDate.isEmpty()) {
//					if(!importModal)
//						throw new IllegalStateException("Slots not created for Date: " + slotDate.getFormattedSlotDate());
//					else
//						continue;
//				}
//				for(BuildMatrixSlot slot: slotsForDate) {
//					BuildMatrixBodyPlant bodyPlant = bodyPlantById.get(slot.getPlantId());
//					BuildMatrixSlotRegionAvailability regionAvailability = regionAvilabilityBySlotId.get(slot.getSlotId());
//					cells.add(new ProductionSlotsUtilizationCell(bodyPlant, slot, regionAvailability));
//				}
				
				for(BuildMatrixBodyPlant plant: bodyPlantList) {
					Map<Integer, Pair<BuildMatrixSlot, BuildMatrixSlotRegionAvailability>> slotAndRegionSlotsBySlotDate = slotAndRegionSlotByDateIdByPlantId.get(plant.getPlantId());
					Pair<BuildMatrixSlot, BuildMatrixSlotRegionAvailability> pair = slotAndRegionSlotsBySlotDate.get(slotDate.getSlotDateId());
					if(pair != null) {
						BuildMatrixSlot slot = pair.getLeft();
						BuildMatrixSlotRegionAvailability ra = pair.getRight();
						cells.add(new ProductionSlotsUtilizationCell(plant, slot, ra));
					}
					else
						cells.add(new ProductionSlotsUtilizationCell(plant, slotDate.getSlotDateId()));
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
		private int slotDateId;
		
		public ProductionSlotsUtilizationCell(BuildMatrixBodyPlant bodyPlant, BuildMatrixSlot slot, 
				BuildMatrixSlotRegionAvailability regionAvailability) {
			this.bodyPlant = bodyPlant;
			this.slot = slot;
			this.regionAvailability = regionAvailability;
			this.slotDateId = slot.getSlotDateId();
		}
		
		public ProductionSlotsUtilizationCell(BuildMatrixBodyPlant bodyPlant, int slotDateId) {
			this.bodyPlant = bodyPlant;
			this.slot = null;
			this.regionAvailability = null;
			this.slotDateId = slotDateId;
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
		public int getSlotDateId() {
			return slotDateId;
		}
	}
	
}
