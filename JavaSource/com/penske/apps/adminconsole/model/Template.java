package com.penske.apps.adminconsole.model;

import java.util.List;

/**
 * 
 * @author 502174985
 *
 */
public class Template {
	private int templateID;
	private String templateName;
	private String templateDesc;
	private String templateHash;
	private String poCatAssID;
	private String createdBy;
	private String modifiedBy;
	private List<Components> componentList;
	
	public int getTemplateID() {
		return templateID;
	}
	public void setTemplateID(int templateID) {
		this.templateID = templateID;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	public String getTemplateHash() {
		return templateHash;
	}
	public void setTemplateHash(String templateHash) {
		this.templateHash = templateHash;
	}
	public String getPoCatAssID() {
		return poCatAssID;
	}
	public void setPoCatAssID(String poCatAssID) {
		this.poCatAssID = poCatAssID;
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
	public List<Components> getComponentList() {
		return componentList;
	}
	public void setComponentList(List<Components> componentList) {
		this.componentList = componentList;
	}
	
}
