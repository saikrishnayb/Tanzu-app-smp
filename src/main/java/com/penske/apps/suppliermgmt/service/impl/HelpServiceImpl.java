/**
 * 
 */
package com.penske.apps.suppliermgmt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.smccore.base.domain.enums.UserType;
import com.penske.apps.suppliermgmt.dao.HelpDAO;
import com.penske.apps.suppliermgmt.service.HelpService;

/**
 * @author 600104283
 *
 */
@Service 
public class HelpServiceImpl implements HelpService {

	@Autowired
	private HelpDAO helpDao;
	
	/* (non-Javadoc)
	 * @see com.penske.apps.suppliermgmt.service.HelpService#getHelp(com.penske.apps.suppliermgmt.model.User)
	 */
	@Override
	public String getHelp(UserType userType) { 
		return helpDao.getHelp(userType);
	}

}
