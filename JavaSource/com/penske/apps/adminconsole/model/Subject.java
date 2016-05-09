package com.penske.apps.adminconsole.model;

/**
 * This class represents subjects created by users.
 * 
 * @author 600143568
 */
public class Subject {
	private int subjectId;
	private String subjectName;
	private int department;
	private int type;
	private int status;

	// Getters
	public int getSubjectId() {
		return subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public int getDepartment() {
		return department;
	}

	public int getType() {
		return type;
	}

	public int getStatus() {
		return status;
	}

	// Setters
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Subject [subjectId=" + subjectId + ", subjectName="
				+ subjectName + ", department=" + department + ", type=" + type
				+ ", status=" + status + "]";
	}
}
