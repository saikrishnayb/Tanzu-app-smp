package com.penske.apps.buildmatrix.domain;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.smccore.base.util.DateUtil;

public class BuildMatrixSlot {
	private int slotId;
	private int slotTypeId;
	private int slotDateId;
	private int plantId;
	private int availableSlots;
	private int reservedSlots;
	private LocalDate chassisArrivalDate;
	private int chassisArrivalWeekOfYear;
	private int chassisArrivalYear;
	private int acceptedSlots;
	private int allocatedRegionSlots;
	
	protected BuildMatrixSlot() {};
	
	public BuildMatrixSlot(BuildMatrixSlotType slotType, BuildMatrixSlotDate slotDate, BuildMatrixBodyPlant plant) {
		this.slotTypeId = slotType.getSlotTypeId();
		this.slotDateId = slotDate.getSlotDateId();
		this.plantId = plant.getPlantId();
		this.availableSlots = 0;
		this.reservedSlots = 0;
		LocalDate chassisArrivalDate = slotDate.getSlotDate().minusWeeks(slotType.getChassisLeadTime());
		this.chassisArrivalDate = chassisArrivalDate;
		WeekFields weekFields = WeekFields.ISO;
		this.chassisArrivalWeekOfYear = chassisArrivalDate.get(weekFields.weekOfWeekBasedYear());
		this.chassisArrivalYear = chassisArrivalDate.getYear();
		this.acceptedSlots = 0;
	}
	
	public String getFormattedChassisArrivalDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(chassisArrivalDate));
	}
	
	public int getUnallocatedSlots() {
		return availableSlots - allocatedRegionSlots;
	}
	
	public boolean isInvalidSlot() {
		if(availableSlots < allocatedRegionSlots)
			return true;
		else
			return false;
	}
	
	public void updateAvailableSlots(int newAvailableSlots) {
		this.availableSlots = newAvailableSlots;
	}

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

	public int getSlotDateId() {
		return slotDateId;
	}
	
	public LocalDate getChassisArrivalDate() {
		return chassisArrivalDate;
	}
	
	public int getChassisArrivalWeekOfYear() {
		return chassisArrivalWeekOfYear;
	}
	
	public int getChassisArrivalYear() {
		return chassisArrivalYear;
	}
	
	public int getAcceptedSlots() {
		return acceptedSlots;
	}
	
	public int getAllocatedRegionSlots() {
		return allocatedRegionSlots;
	}
	
	
}
