package com.penske.apps.adminconsole.model;

public class Order {
	private Integer orderId;
	private String status;
	private String region;
	private String area;
	private String district;
	private String districtName;
	private String programName;
	private int quantity;
	
	public Order(Integer orderId, String status, String region, String area, String district, String districtName,
			String programName, int quantity) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.region = region;
		this.area = area;
		this.district = district;
		this.districtName = districtName;
		this.programName = programName;
		this.quantity = quantity;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public String getStatus() {
		return status;
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
	public String getDistrictName() {
		return districtName;
	}
	public String getProgramName() {
		return programName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
