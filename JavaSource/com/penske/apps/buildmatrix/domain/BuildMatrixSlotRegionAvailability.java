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
	
	public BuildMatrixSlotRegionAvailability(int slotId, String region) {
		this.slotId = slotId;
		this.region = region;
		this.slotAvailable = 0;
		this.slotReserved = 0;
		this.slotAccepted = 0;
	}
	
	// MODIFIED ACCESSORS
	public void markInvalid() {
		this.invalidSlot = true;
	}

	public void markValid() {
		this.invalidSlot = false;
	}
	
	// DEFAULT ACCESSORS	
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

	public void updateAvailableSlots(int newAvailableSlots, BuildMatrixSlot slot, boolean importModal) {
		if(newAvailableSlots + (slot.getAllocatedRegionSlots() - this.slotAvailable) > slot.getAvailableSlots()) {
			this.invalidSlot = true;
			if(!importModal)
				throw new IllegalArgumentException("Available region slots cannot be more than the unallocated overall available slots");
		}
		else if(this.getSlotAccepted() + this.slotReserved > newAvailableSlots) {
			this.invalidSlot = true;
			if(!importModal)
				throw new IllegalArgumentException("Available region slots cannot be less than the already allocated region slots");
		}
		
		this.slotAvailable = newAvailableSlots;
	}

}
