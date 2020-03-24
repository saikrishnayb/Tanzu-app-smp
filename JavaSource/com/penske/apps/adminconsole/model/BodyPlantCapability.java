package com.penske.apps.adminconsole.model;

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

	//getters
	
	/**
	 * @return the capabilityId
	 */
	public int getCapabilityId() {
		return capabilityId;
	}
	
	/**
	 * @return the vehicleCategory
	 */
	public String getVehicleCategory() {
		return vehicleCategory;
	}
	
	/**
	 * @return the optionGroup
	 */
	public String getOptionGroup() {
		return optionGroup;
	}
	
	/**
	 * @return the dimensionValues
	 */
	public List<String> getDimensionValues() {
		return dimensionValues;
	}
	
	/**
	 * @return the selectedDimensions
	 */
	public List<String> getSelectedDimensions() {
		return selectedDimensions;
	}
	
	//setters
	/**
	 * @param optionGroup the optionGroup to set
	 */
	public void setOptionGroup(String optionGroup) {
		this.optionGroup = optionGroup;
	}
	/**
	 * @param vehicleCategory the vehicleCategory to set
	 */
	public void setVehicleCategory(String vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}
	/**
	 * @param capabilityId the capabilityId to set
	 */
	public void setCapabilityId(int capabilityId) {
		this.capabilityId = capabilityId;
	}
	/**
	 * @param dimensionValues the dimensionValues to set
	 */
	public void setDimensionValues(List<String> dimensionValues) {
		this.dimensionValues = dimensionValues;
	}
	/**
	 * @param selectedDimensions the selectedDimensions to set
	 */
	public void setSelectedDimensions(List<String> selectedDimensions) {
		this.selectedDimensions = selectedDimensions;
	}
	
}
