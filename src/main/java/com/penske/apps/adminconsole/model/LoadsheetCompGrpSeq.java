/**
 * 
 */
package com.penske.apps.adminconsole.model;


/**
 * @author 502403391
 * 
 * This class is for assigned components list in loadsheet sequencing
 *
 */
public class LoadsheetCompGrpSeq {
	
	private int compGrpSeqId;
	private int grpMasterId;
	private String componentId;
	private int displaySeq;
	private String createdBy;
	private String modifiedBy;
	
	
	
	//for component details
	private String componentGroup;
	private String subGroup;
	private String componentName;
	
	public int getGrpMasterId() {
		return grpMasterId;
	}
	public void setGrpMasterId(int grpMasterId) {
		this.grpMasterId = grpMasterId;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public int getDisplaySeq() {
		return displaySeq;
	}
	public void setDisplaySeq(int displaySeq) {
		this.displaySeq = displaySeq;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getCompGrpSeqId() {
		return compGrpSeqId;
	}
	public void setCompGrpSeqId(int compGrpSeqId) {
		this.compGrpSeqId = compGrpSeqId;
	}
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
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
	
	

}
