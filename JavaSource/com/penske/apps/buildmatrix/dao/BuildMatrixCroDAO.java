package com.penske.apps.buildmatrix.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.suppliermgmt.annotation.DBCro;

/**
 * @author 600166698
 *
 */
@DBCro
public interface BuildMatrixCroDAO {
	
	public List<ApprovedOrder> getApprovedOrdersForBuildMatrix();

	public List<ApprovedOrder> getApprovedOrdersByIds(@Param("orderKeys") List<CroOrderKey> orderKeys);

}
