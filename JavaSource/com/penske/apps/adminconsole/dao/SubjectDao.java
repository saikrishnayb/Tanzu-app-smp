package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.annotation.NonVendorQuery;
import com.penske.apps.adminconsole.model.Subject;

/**
 * This interface is used for queries to the database for the Subject Management page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */

public interface SubjectDao {
	@NonVendorQuery //TODO: Review Query
	public List<Subject> getAllSubjects();
	
	@NonVendorQuery //TODO: Review Query
	public void addSubject(Subject subject);

	@NonVendorQuery //TODO: Review Query
	public void modifySubject(Subject subject);

	@NonVendorQuery //TODO: Review Query
	public void modifySubjectStatus(int subjectId);
}
