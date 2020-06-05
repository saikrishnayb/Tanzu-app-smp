package com.penske.apps.buildmatrix.service;

import java.util.List;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.buildmatrix.domain.ReportResultOptionModel;

public interface BuildMatrixCroService {
	
	// ***** BUILD MATRIX WORKFLOW *****//
	
	// CRO ORDERS //
	public List<ApprovedOrder> getApprovedOrdersForBuildMatrix();

	public List<ApprovedOrder> getApprovedOrdersByIds(List<CroOrderKey> selectedOrderKeys);

	/**
	 * Returns a list of all the options that are on orders for the Order Report.
	 */
	public List<ReportResultOptionModel> getOrderReportOptions(List<Integer> orderIds);
}
