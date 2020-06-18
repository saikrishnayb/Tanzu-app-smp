package com.penske.apps.buildmatrix.domain;

public class BuildMatrixSlotRegionAvailability {
	private int slotRegionId;
	private int slotId;
	private String region;
	private int slotAvailable;
	private int slotReserved;
	private int slotAccepted;

	protected BuildMatrixSlotRegionAvailability() {}

	public BuildMatrixSlotRegionAvailability(BuildMatrixSlot slot, RegionPlantAssociation assoc) {
		this.slotId = slot.getSlotId();
		this.region = assoc.getRegion();
		this.slotAvailable = 0;
		this.slotReserved = 0;
		this.slotAccepted = 0;
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
	};
	
}
