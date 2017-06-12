package com.penske.apps.adminconsole.model;

public class LoadSheetComponentDetails {
	
	
	private String componentGroup;
	private String subGroup;
	private String componentId;
	private String componentType;
	private String componentName;
	public String getComponentGroup() {
		return componentGroup;
	}
	public void setComponentGroup(String componentGroup) {
		this.componentGroup = componentGroup;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
	
	//implementing hash code and equals method to remove the assigned components list from unassigned list 
	@Override
	 public int hashCode() {
		 return componentId.hashCode();
	 }
	
	 @Override
	    public boolean equals(Object obj) {
			 
		 LoadsheetCompGrpSeq cmpGrpSeq=(LoadsheetCompGrpSeq)obj;

	       return this.componentId.equals(cmpGrpSeq.getComponentId());
	    }
		
	
	
	
	

}
