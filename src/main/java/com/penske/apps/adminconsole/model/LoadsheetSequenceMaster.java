package com.penske.apps.adminconsole.model;

import java.util.Date;
import java.util.List;
import com.penske.apps.smccore.base.util.DateUtil;

/**
 * 
 * @author 505001934
 * This class represents model for component rule mapping
 */
public class LoadsheetSequenceMaster {
	private int id;
	private String name;
	private String description;
	private String category;
	private String type;
	private String oem;
	private String editedBy;
	private Date 	editedDate;
	private String viewMode; // for back button functionality
	private List<LoadsheetSequenceGroupMaster> groupMasterList;
	
	private String pageAction;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOem() {
		return oem;
	}
	public void setOem(String oem) {
		this.oem = oem;
	}
	public String getEditedBy() {
		return editedBy;
	}
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}
	public Date getEditedDate() {
		return editedDate;
	}
	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}
	public String getFmtEditedDate() {
		return DateUtil.formatDateTimeUS(this.editedDate);
	}
	public List<LoadsheetSequenceGroupMaster> getGroupMasterList() {
		return groupMasterList;
	}
	public void setGroupMasterList(
			List<LoadsheetSequenceGroupMaster> groupMasterList) {
		this.groupMasterList = groupMasterList;
	}
	public String getPageAction() {
		return pageAction;
	}
	public void setPageAction(String pageAction) {
		this.pageAction = pageAction;
	}
	public String getViewMode() {
		return viewMode;
	}
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}
	
	
}
