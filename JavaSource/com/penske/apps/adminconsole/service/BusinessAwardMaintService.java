package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.OEM;

public interface BusinessAwardMaintService {
	
	public List<OEM> getAllOEMs();
	
	public List<String> getAllPoCategory();
	
	public List<String> getAllOEMNames();
	
}
