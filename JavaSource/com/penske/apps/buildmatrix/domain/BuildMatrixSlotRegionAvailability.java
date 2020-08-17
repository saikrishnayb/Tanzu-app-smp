package com.penske.apps.buildmatrix.domain;

public class BuildMatrixSlotRegionAvailability {
	private int slotRegionId;
	private int slotId;
	private String region;
	private int slotAvailable;
	private int slotReserved;
	private int slotAccepted;
	private boolean invalidSlot;

	protected BuildMatrixSlotRegionAvailability() {}

	public BuildMatrixSlotRegionAvailability(int slotId, RegionPlantAssociation assoc) {
		this.slotId = slotId;
		this.region = assoc.getRegion();
		this.slotAvailable = 0;
		this.slotReserved = 0;
		this.slotAccepted = 0;
	}
	
	public int getAllocatedSlots() {
		return slotReserved + slotAccepted;
	}

	public int getSlotRegionId() {
		return slotRegionId;
	}

	public int getSlotId() {
		return slotId;
	}

	public String getRegion() {
		return region;
	}

	public int getSlotAvailable() {
		return slotAvailable;
	}

	public int getSlotReserved() {
		return slotReserved;
	}

	public int getSlotAccepted() {
		return slotAccepted;
	}
	
	public boolean isInvalidSlot() {
		return invalidSlot;
	}

	public void updateAvailableSlots(int newAvailableSlots, BuildMatrixSlot slot) {
		if(newAvailableSlots + (slot.getAllocatedRegionSlots() - this.slotAvailable) > slot.getAvailableSlots()) {
			this.invalidSlot = true;
		}
		else if(this.getSlotAccepted() + this.slotReserved > newAvailableSlots) {
			this.invalidSlot = true;
		}
		else {
			this.invalidSlot = false;
		}
		
		this.slotAvailable = newAvailableSlots;
	}


}
