package com.penske.apps.buildmatrix.model;

import java.util.List;

public class SaveRegionSlotsForm {

	private int slotTypeId;
	private int year;
	private String region;
	private List<RegionSlotInfo> regionSlotInfos;
	
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
		private int slotAvailable;
		
		public int getSlotAvailable() {
			return slotAvailable;
		}
		
		public int getSlotRegionId() {
			return slotRegionId;
		}
		
		public void setSlotAvailable(int slotAvailable) {
			this.slotAvailable = slotAvailable;
		}
		
		public void setSlotRegionId(int slotRegionId) {
			this.slotRegionId = slotRegionId;
		}

	}
}
