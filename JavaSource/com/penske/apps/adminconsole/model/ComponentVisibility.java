package com.penske.apps.adminconsole.model;
/**
 * 
 * @author 600144005
 * This class represents all visible components
 */
public class ComponentVisibility {
	private String componentName;
	private int componentId;
	private String visibility;
	private PoCategory category;
	private SubCategory subCategory;
	private String isComponentVehicle;
	private String vehicleId;
	private String vehicleName;
	private PoCategory vehicleCategory;
	private SubCategory vehicleSubCategory;
	private String vehicleVisibility;

	// Getters
	public String getComponentName() {
		return componentName;
	}

	public int getComponentId() {
		return componentId;
	}



	public PoCategory getCategory() {
		return category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	
	public String getIsComponentVehicle() {
		return isComponentVehicle;
	}

	public void setIsComponentVehicle(String isComponentVehicle) {
		if("Y".equalsIgnoreCase(isComponentVehicle))
				this.isComponentVehicle = "Yes";
		else
				this.isComponentVehicle = "No";
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public PoCategory getVehicleCategory() {
		return vehicleCategory;
	}

	public SubCategory getVehicleSubCategory() {
		return vehicleSubCategory;
	}

	public String getVehicleVisibility() {
		return vehicleVisibility;
	}

	// Setters
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	
	public void setCategory(PoCategory category) {
		this.category = category;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public void setVehicleCategory(PoCategory vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}

	public void setVehicleSubCategory(SubCategory vehicleSubCategory) {
		this.vehicleSubCategory = vehicleSubCategory;
	}

	public void setVehicleVisibility(String vehicleVisibility) {
		this.vehicleVisibility = vehicleVisibility;
	}

	@Override
	public String toString() {
		return "ComponentVisibility [componentName=" + componentName
				+ ", componentId=" + componentId + ", visibility=" + visibility
				+ ", category=" + category + ", subCategory=" + subCategory
				+ ", isComponentVehicle=" + isComponentVehicle + "]";
	}

}
