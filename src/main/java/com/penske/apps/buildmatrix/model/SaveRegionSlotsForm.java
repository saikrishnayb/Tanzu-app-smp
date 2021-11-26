package com.penske.apps.buildmatrix.model;

import java.util.List;

public class SaveRegionSlotsForm {

	private int slotTypeId;
	private int year;
	private String region;
	private List<RegionSlotInfo> regionSlotInfos;
	
	public boolean needToCreateSlots() {
		return regionSlotInfos.stream()
				.anyMatch(si -> si.getSlotId() == -1);
	}
	
	public boolean needToCreateRegionSlots() {
		return regionSlotInfos.stream()
				.anyMatch(si -> si.getSlotRegionId() == -1);
	}
	
	public List<RegionSlotInfo> getRegionSlotInfos() {
		return regionSlotInfos;
	}
	
	public int getSlotTypeId() {
		return slotTypeId;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegionSlotInfos(List<RegionSlotInfo> regionSlotInfos) {
		this.regionSlotInfos = regionSlotInfos;
	}
	
	public void setSlotTypeId(int slotTypeId) {
		this.slotTypeId = slotTypeId;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public static class RegionSlotInfo {
		private int slotRegionId;
		private int slotId;
		private int slotAvailable;
		private int slotDateId;
		private int plantId;
		
		public int getSlotAvailable() {
			return slotAvailable;
		}
		
		public int getSlotId() {
			return slotId;
		}
		
		public int getSlotRegionId() {
			return slotRegionId;
		}
		
		public int getPlantId() {
			return plantId;
		}
		
		public int getSlotDateId() {
			return slotDateId;
		}
		
		public void setSlotAvailable(int slotAvailable) {
			this.slotAvailable = slotAvailable;
		}
		
		public void setSlotRegionId(int slotRegionId) {
			this.slotRegionId = slotRegionId;
		}
		
		public void setPlantId(int plantId) {
			this.plantId = plantId;
		}
		
		public void setSlotDateId(int slotDateId) {
			this.slotDateId = slotDateId;
		}
		
		public void setSlotId(int slotId) {
			this.slotId = slotId;
		}

	}
}
