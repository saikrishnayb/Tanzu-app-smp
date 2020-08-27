package com.penske.apps.buildmatrix.model;

import java.util.List;

public class InvalidSlotsForm {
	private int plantId;
	private int slotTypeId;
	private List<InvalidSlotInfo> invalidSlotInfos;
	
	public int getPlantId() {
		return plantId;
	}
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public int getSlotTypeId() {
		return slotTypeId;
	}
	public void setSlotTypeId(int slotTypeId) {
		this.slotTypeId = slotTypeId;
	}
	public List<InvalidSlotInfo> getInvalidSlotInfos() {
		return invalidSlotInfos;
	}
	public void setInvalidSlotInfos(List<InvalidSlotInfo> invalidSlotInfos) {
		this.invalidSlotInfos = invalidSlotInfos;
	}

	public static class InvalidSlotInfo {
		private int slotRegionId;
		private int slotId;
		private int slotDateId;
		private String region;
		private int slotAvailable;
		
		public int getSlotRegionId() {
			return slotRegionId;
		}
		public void setSlotRegionId(int slotRegionId) {
			this.slotRegionId = slotRegionId;
		}
		public int getSlotId() {
			return slotId;
		}
		public void setSlotId(int slotId) {
			this.slotId = slotId;
		}
		public int getSlotDateId() {
			return slotDateId;
		}
		public void setSlotDateId(int slotDateId) {
			this.slotDateId = slotDateId;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public int getSlotAvailable() {
			return slotAvailable;
		}
		public void setSlotAvailable(int slotAvailable) {
			this.slotAvailable = slotAvailable;
		}
	}

}
