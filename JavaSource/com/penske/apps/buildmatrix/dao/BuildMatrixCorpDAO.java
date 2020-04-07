package com.penske.apps.buildmatrix.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.adminconsole.enums.PoCategoryType;
import com.penske.apps.buildmatrix.domain.AvailableChassis;
import com.penske.apps.buildmatrix.model.ManufacturerDetails;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * @author 600166698
 *
 */
@DBSmc
public interface BuildMatrixCorpDAO {
	
	public int getAvailableChassisCount();
	
	public List<AvailableChassis> getAvailableChassis();
	
	public List<ManufacturerDetails> getManufacturersByType(@Param("poCategoryType") PoCategoryType poCategoryType);

}
