package com.penske.apps.adminconsole.domain;


public class ComponentInfoDetail {
    
    private int componentId;
    private String displayName;
    private int compGroupId;
    private char componentType;
    private boolean vehicleComponent;
    private boolean visibility;
    private int componentSequence;

    //MyBatis Only
    protected ComponentInfoDetail() {}

    /** Modified Accessors */
    public void setVehicleComponent(String vehicleComponent) {
        this.vehicleComponent = "Y".equals(vehicleComponent);
    }
    public void setVisibility(String visibility) {
        this.visibility = "Y".equals(visibility);
    }
    
    
    public int getComponentId() {
        return componentId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public int getCompGroupId() {
        return compGroupId;
    }
    public char getComponentType() {
        return componentType;
    }
    public boolean isVehicleComponent() {
        return vehicleComponent;
    }
    public boolean isVisibility() {
        return visibility;
    }
    public int getComponentSequence() {
        return componentSequence;
    }

    @Override
    public String toString() {

        return "ComponentInfoDetails [componentId=" + componentId + ", displayName=" + displayName + ", compGroupId=" + compGroupId
                + ", componentType=" + componentType + ", vehicleComponent=" + vehicleComponent + ", visibility=" + visibility
                + ", componentSequence=" + componentSequence + "]";
    }
    
}
