package com.penske.apps.buildmatrix.domain;

import java.util.List;

public class BodyPlantCapability {
	
	private int capabilityId;
	private String vehicleCategory;
	private String optionGroup;
	private List<String> dimensionValues;
	private List<String> selectedDimensions;
	
	public BodyPlantCapability(int capabilityId, String vehicleCategory, String optionGroup,
			List<String> dimensionValues, List<String> selectedDimensions) {
		super();
		this.capabilityId = capabilityId;
		this.vehicleCategory = vehicleCategory;
		this.optionGroup = optionGroup;
		this.dimensionValues = dimensionValues;
		this.selectedDimensions = selectedDimensions;
	}

	//***** DEFAULT ACCESSORS *****//
	public int getCapabilityId() {
		return capabilityId;
	}
	
	public String getVehicleCategory() {
		return vehicleCategory;
	}
	
	public String getOptionGroup() {
		return optionGroup;
	}
	
	public List<String> getDimensionValues() {
		return dimensionValues;
	}
	
	public List<String> getSelectedDimensions() {
		return selectedDimensions;
	}
	
	public void setOptionGroup(String optionGroup) {
		this.optionGroup = optionGroup;
	}
	
	public void setVehicleCategory(String vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}
	
	public void setCapabilityId(int capabilityId) {
		this.capabilityId = capabilityId;
	}
	
	public void setDimensionValues(List<String> dimensionValues) {
		this.dimensionValues = dimensionValues;
	}
	
	public void setSelectedDimensions(List<String> selectedDimensions) {
		this.selectedDimensions = selectedDimensions;
	}
	
}
