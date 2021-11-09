package com.penske.apps.buildmatrix.model;

public class BuildMatrixSlotKey {
	
	private int slotDateId;
	private int plantId;
	private int slotTypeId;
	
	public BuildMatrixSlotKey(int slotDateId, int plantId, int slotTypeId) {
		this.slotDateId = slotDateId;
		this.plantId = plantId;
		this.slotTypeId = slotTypeId;
	}

	public int getSlotDateId() {
		return slotDateId;
	}

	public void setSlotDateId(int slotDateId) {
		this.slotDateId = slotDateId;
	}

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

	@Override
	public String toString() {
		return "BuildMatrixSlotKey [slotDateId=" + slotDateId + ", plantId=" + plantId + ", slotTypeId=" + slotTypeId
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + plantId;
		result = prime * result + slotDateId;
		result = prime * result + slotTypeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildMatrixSlotKey other = (BuildMatrixSlotKey) obj;
		if (plantId != other.plantId)
			return false;
		if (slotDateId != other.slotDateId)
			return false;
		if (slotTypeId != other.slotTypeId)
			return false;
		return true;
	}
	
}
