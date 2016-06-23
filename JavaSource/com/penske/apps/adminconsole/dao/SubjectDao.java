package com.penske.apps.adminconsole.dao;

import java.util.List;

import com.penske.apps.adminconsole.model.Subject;

/**
 * This interface is used for queries to the database for the Subject Management page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */

public interface SubjectDao {
	public List<Subject> getAllSubjects();
	
	public void addSubject(Subject subject);

	public void modifySubject(Subject subject);

	public void modifySubjectStatus(int subjectId);
}
