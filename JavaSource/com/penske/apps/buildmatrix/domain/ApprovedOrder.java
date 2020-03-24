package com.penske.apps.buildmatrix.domain;

import java.sql.Date;

import com.penske.apps.buildmatrix.domain.enums.ApprovalStatus;

public class ApprovedOrder {
	
	private int orderId;
	private int deliveryId;
	private String locationNumber;
	private Date deliveryDate;
	private String region;
	private String area;
	private String district;
	private String districtDesc;
	private int addCount;
	private int replaceCount;
	private String packageName;
	private ApprovalStatus approvalStatus;
	
	/** Null constructor - MyBatis only */
	protected ApprovedOrder() {}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "{ApprovedOrder (Order ID: " + orderId + "), deliveryId: " + deliveryId +", locationNumber: " + locationNumber + "}";
	}
	
	//***** MODIFIED ACCESSORS *****//
	public int getOrderTotalQuantity() {
		return addCount + replaceCount;
	}
	
	//***** DEFAULT ACCESSORS *****//
	public int getOrderId() {
		return orderId;
	}

	public int getDeliveryId() {
		return deliveryId;
	}

	public String getLocationNumber() {
		return locationNumber;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public String getRegion() {
		return region;
	}

	public String getArea() {
		return area;
	}

	public String getDistrict() {
		return district;
	}

	public String getDistrictDesc() {
		return districtDesc;
	}

	public int getAddCount() {
		return addCount;
	}

	public int getReplaceCount() {
		return replaceCount;
	}

	public String getPackageName() {
		return packageName;
	}
	
	public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}
	
}
