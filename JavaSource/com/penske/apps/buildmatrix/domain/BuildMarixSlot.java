package com.penske.apps.buildmatrix.domain;

public class BuildMarixSlot {
	private int slotId;
	private int slotTypeId;
	private int plantId;
	private int availableSlots;
	private int reservedSlots;

	public int getSlotId() {
		return slotId;
	}

	public int getSlotTypeId() {
		return slotTypeId;
	}

	public int getPlantId() {
		return plantId;
	}

	public int getAvailableSlots() {
		return availableSlots;
	}

	public int getReservedSlots() {
		return reservedSlots;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	public void setSlotTypeId(int slotTypeId) {
		this.slotTypeId = slotTypeId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public void setAvailableSlots(int availableSlots) {
		this.availableSlots = availableSlots;
	}

	public void setReservedSlots(int reservedSlots) {
		this.reservedSlots = reservedSlots;
	}
}
