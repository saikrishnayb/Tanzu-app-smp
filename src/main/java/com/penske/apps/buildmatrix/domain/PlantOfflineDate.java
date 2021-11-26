package com.penske.apps.buildmatrix.domain;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import com.penske.apps.smccore.base.util.DateUtil;

public class PlantOfflineDate {
	private int offlineDateId;
	private int plantId;
	private Date offlineStartDate;
	private Date offlineEndDate;

	public int getOfflineDateId() {
		return offlineDateId;
	}

	public int getPlantId() {
		return plantId;
	}

	public Date getOfflineStartDate() {
		return offlineStartDate;
	}

	public Date getOfflineEndDate() {
		return offlineEndDate;
	}

	public void setOfflineDateId(int offlineDateId) {
		this.offlineDateId = offlineDateId;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public void setOfflineStartDate(Date offlineStartDate) {
		this.offlineStartDate = offlineStartDate;
	}

	public void setOfflineEndDate(Date offlineEndDate) {
		this.offlineEndDate = offlineEndDate;
	}

	public String getFormattedOfflineStartDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(offlineStartDate));
	}

	public String getFormattedOfflineEndDate() {
		return StringUtils.defaultString(DateUtil.formatDateUS(offlineEndDate));
	}
}
