package com.penske.apps.buildmatrix.model;

import java.util.List;

public class SaveSlotsForm {

	private int slotTypeId;
	private int year;
	private List<SlotInfo> slotInfos;
	
	public boolean needToCreateSlots() {
		return slotInfos.stream()
				.anyMatch(si -> si.getSlotId() == -1);
	}
	
	public List<SlotInfo> getSlotInfos() {
		return slotInfos;
	}
	
	public int getSlotTypeId() {
		return slotTypeId;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setSlotInfos(List<SlotInfo> slotInfos) {
		this.slotInfos = slotInfos;
	}
	
	public void setSlotTypeId(int slotTypeId) {
		this.slotTypeId = slotTypeId;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public static class SlotInfo {
		private int slotId;
		private int plantId;
		private int slotDateId;
		private int availableSlots;
		
		public int getAvailableSlots() {
			return availableSlots;
		}
		public int getSlotId() {
			return slotId;
		}
		public int getPlantId() {
			return plantId;
		}
		public int getSlotDateId() {
			return slotDateId;
		}
		public void setAvailableSlots(int availableSlots) {
			this.availableSlots = availableSlots;
		}
		public void setSlotId(int slotId) {
			this.slotId = slotId;
		}
		public void setPlantId(int plantId) {
			this.plantId = plantId;
		}
		public void setSlotDateId(int slotDateId) {
			this.slotDateId = slotDateId;
		}
	}
}
