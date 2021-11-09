package com.penske.apps.buildmatrix.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.ReportResultOptionModel;
import com.penske.apps.suppliermgmt.annotation.DBCro;

/**
 * @author 600166698
 *
 */
@DBCro
public interface BuildMatrixCroDAO {
	
	// ***** BUILD MATRIX WORKFLOW *****//
	
	// CRO ORDERS //
	public List<ApprovedOrder> getApprovedOrdersForBuildMatrix();

	public List<ApprovedOrder> getApprovedOrdersByIds(@Param("orderKeys") List<CroOrderKey> orderKeys);

	/**
	 * Returns a list of all the options that are on orders for the Order Report.
	 */
	public List<ReportResultOptionModel> getOrderReportOptions(@Param("orderIds") List<Integer> orderIds);
}
