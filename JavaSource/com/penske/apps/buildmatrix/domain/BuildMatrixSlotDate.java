package com.penske.apps.buildmatrix.domain;

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


	public void setSlotDateId(int slotDateId) {
		this.slotDateId = slotDateId;
	}

	public void setSlotYear(String slotYear) {
		this.slotYear = slotYear;
	}

	public void setSlotDate(Date slotDate) {
		this.slotDate = slotDate;
	}

	public void setWeekOfYear(int weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	
	public void setBuildSlots(List<BuildMatrixSlot> buildSlots) { 
		this.buildSlots = buildSlots;
	}
	

	public String getFormattedSlotDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(slotDate));
	}
}
