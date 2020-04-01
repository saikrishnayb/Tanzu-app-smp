package com.penske.apps.buildmatrix.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.suppliermgmt.annotation.DBSmc;

/**
 * @author 600166698
 *
 */
@DBSmc
public interface BuildMatrixSmcDAO {
	
	public List<BuildSummary> getAllBuildHistory();

	public void insertNewBuild(@Param("newBuild") BuildSummary newBuild);

	public void insertCroBuildRequest(@Param("buildId") int buildId, @Param("order") ApprovedOrder order);

	public void updateBuild(@Param("existingBuild") BuildSummary existingBuild);

	public void deleteCroBuildRequestsFromBuild(@Param("existingBuildId") Integer existingBuildId);

	public BuildSummary getBuildSummary(@Param("buildId") Integer buildId);

	public List<CroOrderKey> getCroOrderKeysForBuild(@Param("buildId") Integer buildId);
	
	public List<BuildAttribute> getAttributesForBuild();
}
