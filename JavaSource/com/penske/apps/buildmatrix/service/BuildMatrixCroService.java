package com.penske.apps.buildmatrix.service;

import java.util.List;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.CroOrderKey;

public interface BuildMatrixCroService {
	
	public List<ApprovedOrder> getApprovedOrdersForBuildMatrix();

	public List<ApprovedOrder> getApprovedOrdersByIds(List<CroOrderKey> selectedOrderKeys);

}
