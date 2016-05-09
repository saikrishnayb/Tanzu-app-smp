package com.penske.apps.adminconsole.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.dao.SubjectDao;
import com.penske.apps.adminconsole.model.Subject;

/**
 * This class is used for queries to the database for the Subject Management page in the Admin Console under the App Config tab.
 * 
 * @author 600143568
 */
@Service
public class DefaultSubjectService implements SubjectService {

	@Autowired
	private SubjectDao subjectDao;
	
	@Override
	public List<Subject> getAllSubjects() {
		return subjectDao.getAllSubjects();
	}
	
	@Override
	public void addSubject(Subject subject) {
		if (validateSubject(subject)) {
			subjectDao.addSubject(subject);
		}
	}

	@Override
	public void modifySubject(Subject subject) {
		if (validateSubject(subject)) {
			subjectDao.modifySubject(subject);
		}
	}

	@Override
	public void modifySubjectStatus(int subjectId) {
		// The subject ID must be positive.
		if (subjectId > 0) {
			subjectDao.modifySubjectStatus(subjectId);
		}
	}
	
	public boolean validateSubject(Subject subject) {
		// Subject Name cannot be null or blank.
		if (StringUtils.isEmpty(subject.getSubjectName())) {
			return false;
		}
		// Department cannot be negative.
		else if (subject.getDepartment() < 0) {
			return false;
		}
		// Type cannot be negative.
		else if (subject.getType() < 0) {
			return false;
		}
				
		return true;
	}
}
