package com.penske.apps.buildmatrix.domain;

public class RegionPlantAssociation {
	private int plantId;
	private int regionAssociationId;
	private String region;
	private String regionDesc;
	private String isAssociated;

	public int getPlantId() {
		return plantId;
	}

	public int getRegionAssociationId() {
		return regionAssociationId;
	}

	public String getRegion() {
		return region;
	}

	public String getRegionDesc() {
		return regionDesc;
	}

	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}

	public void setRegionAssociationId(int regionAssociationId) {
		this.regionAssociationId = regionAssociationId;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setRegionDesc(String regionDesc) {
		this.regionDesc = regionDesc;
	}

	public String getIsAssociated() {
		return isAssociated;
	}

	public void setIsAssociated(String isAssociated) {
		this.isAssociated = isAssociated;
	}
}
