package com.penske.apps.buildmatrix.dao;

import java.util.List;

import com.penske.apps.buildmatrix.domain.AvailableChassis;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * @author 600166698
 *
 */
@DBSmc
public interface BuildMatrixCorpDAO {
	
	public int getAvailableChassisCount();
	
	public List<AvailableChassis> getAvailableChassis();
	
}
