package com.penske.apps.buildmatrix.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.penske.apps.smccore.base.util.DateUtil;

public class BuildMatrixSlotDate {
	private int slotDateId;
	private String slotYear;
	private Date slotDate;
	private int weekOfYear;
	private List<BuildMatrixSlot> buildSlots;

	protected BuildMatrixSlotDate() {};
	
	public BuildMatrixSlotDate(LocalDate date) {
		this.slotYear = String.valueOf(date.getYear());
		this.slotDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
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

	public Date getSlotDate() {
		return slotDate;
	}

	public int getWeekOfYear() {
		return weekOfYear;
	}


	public List<BuildMatrixSlot> getBuildSlots() { return buildSlots; }

	public String getFormattedSlotDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(slotDate));
	}
}
