package com.penske.apps.buildmatrix.model;

import java.util.List;

import com.penske.apps.buildmatrix.domain.RegionPlantAssociation;

public class SavePlantRegionForm {
	private Integer plantId;
	private List<RegionPlantAssociation> regionPlantAssociationList;
	
	public Integer getPlantId() {
		return plantId;
	}
	public List<RegionPlantAssociation> getRegionPlantAssociationList() {
		return regionPlantAssociationList;
	}
	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}
	public void setRegionPlantAssociationList(List<RegionPlantAssociation> regionPlantAssociationList) {
		this.regionPlantAssociationList = regionPlantAssociationList;
	}
}
