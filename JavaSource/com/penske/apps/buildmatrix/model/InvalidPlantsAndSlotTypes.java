package com.penske.apps.buildmatrix.model;

import java.util.List;

import com.penske.apps.buildmatrix.domain.BuildMatrixBodyPlant;
import com.penske.apps.buildmatrix.domain.BuildMatrixSlotType;

public class InvalidPlantsAndSlotTypes {
	private List<BuildMatrixBodyPlant> invalidBodyPlants;
	private List<BuildMatrixSlotType> invalidSlotTypes;
	
	public InvalidPlantsAndSlotTypes(List<BuildMatrixBodyPlant> invalidBodyPlants, List<BuildMatrixSlotType> invalidSlotTypes) {
		this.invalidBodyPlants = invalidBodyPlants;
		this.invalidSlotTypes = invalidSlotTypes;
	}

	public List<BuildMatrixBodyPlant> getInvalidBodyPlants() {
		return invalidBodyPlants;
	}
	
	public List<BuildMatrixSlotType> getInvalidSlotTypes() {
		return invalidSlotTypes;
	}
}
