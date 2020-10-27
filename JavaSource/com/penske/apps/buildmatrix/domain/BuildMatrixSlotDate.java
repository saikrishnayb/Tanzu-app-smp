package com.penske.apps.buildmatrix.domain;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.smccore.base.util.DateUtil;

public class BuildMatrixSlotDate {
	private int slotDateId;
	private String slotYear;
	private LocalDate slotDate;
	private int weekOfYear;
	private List<BuildMatrixSlot> buildSlots;
	private int slotId;

	protected BuildMatrixSlotDate() {};
	
	public BuildMatrixSlotDate(LocalDate date, int slotYear) {
		this.slotYear = String.valueOf(slotYear);
		this.slotDate = date;
		WeekFields weekFields = WeekFields.ISO;
		int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
		this.weekOfYear = weekNumber;
	}

	public int getSlotDateId() {
		return slotDateId;
	}

	public String getSlotYear() {
		return slotYear;
	}

	public LocalDate getSlotDate() {
		return slotDate;
	}

	public int getWeekOfYear() {
		return weekOfYear;
	}

	public int getSlotId() {
		return slotId;
	}

	public List<BuildMatrixSlot> getBuildSlots() { return buildSlots; }

	public String getFormattedSlotDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(slotDate));
	}
}
