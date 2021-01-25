package com.penske.apps.adminconsole.model;

import org.apache.commons.lang3.StringUtils;

/**
 * This is the basic model object for a Cost Adjustment Option, which is
 * displayed in the datatable on the Cost Sheet Adjustment Options page.
 * 
 * @author Maxim.Timofeev
 */
public class CostAdjustmentOption {
	private int optionId;
	private String orderCode;

	@Override
	public String toString() {
		return "CostAdjustmentOption [optionId=" + optionId + ", orderCode=" + orderCode + "]";
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = StringUtils.trim(orderCode);
	}
}
