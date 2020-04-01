package com.penske.apps.buildmatrix.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.buildmatrix.dao.BuildMatrixSmcDAO;
import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.BuildAttribute;
import com.penske.apps.buildmatrix.domain.BuildSummary;
import com.penske.apps.buildmatrix.domain.CroOrderKey;
import com.penske.apps.suppliermgmt.model.UserContext;

@Service
public class DefaultBuildMatrixSmcService implements BuildMatrixSmcService {

	@Autowired
	BuildMatrixSmcDAO buildMatrixSmcDAO;
	
	@Override
	public List<BuildSummary> getAllBuildHistory() {
		return buildMatrixSmcDAO.getAllBuildHistory();
	}
	
	@Override
	public BuildSummary startNewBuild(List<ApprovedOrder> selectedOrders, UserContext userContext) {
		int bodiesOnOrder = selectedOrders.stream().collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		BuildSummary newBuild = new BuildSummary(bodiesOnOrder, userContext);
		buildMatrixSmcDAO.insertNewBuild(newBuild);
		int buildId = newBuild.getBuildId();
		for(ApprovedOrder order: selectedOrders) {
			buildMatrixSmcDAO.insertCroBuildRequest(buildId, order);
		}
		return newBuild;
	}
	
	@Override
	public BuildSummary updateExistingBuild(Integer buildId, List<ApprovedOrder> selectedOrders) {
		int bodiesOnOrder = selectedOrders.stream().collect(Collectors.summingInt(order->order.getOrderTotalQuantity()));
		BuildSummary existingBuild = buildMatrixSmcDAO.getBuildSummary(buildId);
		existingBuild.setQuantity(bodiesOnOrder);
		buildMatrixSmcDAO.updateBuild(existingBuild);
		Integer existingBuildId = existingBuild.getBuildId();
		buildMatrixSmcDAO.deleteCroBuildRequestsFromBuild(existingBuildId);
		for(ApprovedOrder order: selectedOrders) {
			buildMatrixSmcDAO.insertCroBuildRequest(buildId, order);
		}
		return existingBuild;
	}
	
	@Override
	public BuildSummary getBuildSummary(Integer buildId) {
		return buildMatrixSmcDAO.getBuildSummary(buildId);
	}
	
	@Override
	public List<CroOrderKey> getCroOrderKeysForBuild(Integer buildId) {
		return buildMatrixSmcDAO.getCroOrderKeysForBuild(buildId);
	}
	
	@Override
	public List<BuildAttribute> getAttributesForBuild() {
		return buildMatrixSmcDAO.getAttributesForBuild();
	}
}
