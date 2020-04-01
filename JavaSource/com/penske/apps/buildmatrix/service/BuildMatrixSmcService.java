package com.penske.apps.buildmatrix.service;

import java.util.List;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.suppliermgmt.model.UserContext;

public interface BuildMatrixSmcService {
	
	public List<BuildSummary> getAllBuildHistory();

	public BuildSummary startNewBuild(List<ApprovedOrder> selectedOrders, UserContext userContext);

	public BuildSummary updateExistingBuild(Integer buildId, List<ApprovedOrder> selectedOrders);

	public BuildSummary getBuildSummary(Integer buildId);

	public List<CroOrderKey> getCroOrderKeysForBuild(Integer buildId);

	public List<BuildAttribute> getAttributesForBuild();
}
