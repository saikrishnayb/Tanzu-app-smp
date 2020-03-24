package com.penske.apps.buildmatrix.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penske.apps.buildmatrix.dao.BuildMatrixCroDAO;
import com.penske.apps.buildmatrix.domain.ApprovedOrder;
import com.penske.apps.buildmatrix.domain.CroOrderKey;

@Service
public class DefaultBuildMatrixCroService implements BuildMatrixCroService {

	@Autowired
	BuildMatrixCroDAO buildMatrixCroDAO;
	
	@Override
	public List<ApprovedOrder> getApprovedOrdersForBuildMatrix() {
		return buildMatrixCroDAO.getApprovedOrdersForBuildMatrix();
	}
	
	@Override
	public List<ApprovedOrder> getApprovedOrdersByIds(List<CroOrderKey> orderIds) {
		List<ApprovedOrder> approvedOrders = new ArrayList<>();
		List<List<CroOrderKey>> subSets = ListUtils.partition(orderIds, 1000);
		for(List<CroOrderKey> list: subSets) {
			approvedOrders.addAll(buildMatrixCroDAO.getApprovedOrdersByIds(list));
		}
		return approvedOrders;
	}

}
