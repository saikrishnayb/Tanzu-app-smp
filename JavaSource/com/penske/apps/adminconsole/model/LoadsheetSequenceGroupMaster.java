/**
 * 
 */
package com.penske.apps.adminconsole.model;

import java.util.List;

/**
 * @author 502403391
 * 
 *this model is for loadsheet group details
 */
public class LoadsheetSequenceGroupMaster {

	private int grpMasterId;
	private int seqMasterId;
	private String name;
	private int displaySeq;
	private String createdBy;
	private String modifiedBy;
	
	
	private List<LoadsheetCompGrpSeq> compGrpSeqList;
	
	public int getSeqMasterId() {
		return seqMasterId;
	}
	public void setSeqMasterId(int seqMasterId) {
		this.seqMasterId = seqMasterId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List<LoadsheetCompGrpSeq> getCompGrpSeqList() {
		return compGrpSeqList;
	}
	public void setCompGrpSeqList(List<LoadsheetCompGrpSeq> compGrpSeqList) {
		this.compGrpSeqList = compGrpSeqList;
	}
	public int getGrpMasterId() {
		return grpMasterId;
	}
	public void setGrpMasterId(int grpMasterId) {
		this.grpMasterId = grpMasterId;
	}
	
	
	
	
}
