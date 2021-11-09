package com.penske.apps.buildmatrix.model;

import java.util.List;
import java.util.Map;

import com.penske.apps.buildmatrix.domain.CroOrderKey;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class OrderSelectionForm {
	
	private Integer buildId;
	private List<OrderSelection> selectedOrders;
	private boolean guidance;

	//***** MODIFIED ACCESSORS *****//
	public List<CroOrderKey> getCroOrderKeys(){
		List<CroOrderKey> croOrderKeys = selectedOrders.stream().map(so -> new CroOrderKey(so.getOrderId(), so.getDeliveryId())).collect(toList());
		return croOrderKeys;
	}
	
	public Map<CroOrderKey, Integer> getUnitsToConsiderByCroOrderKey(){
		Map<CroOrderKey, Integer> unitsToConsiderByCroOrderKey = selectedOrders.stream()
				.collect(toMap(so -> new CroOrderKey(so.getOrderId(), so.getDeliveryId()), so -> so.getUnitsToConsider()));
		return unitsToConsiderByCroOrderKey;
	}
	
	//***** DEFUALT ACCESSORS *****//
	public Integer getBuildId() {
		return buildId;
	}

	public void setBuildId(Integer buildId) {
		this.buildId = buildId;
	}

	public List<OrderSelection> getSelectedOrders() {
		return selectedOrders;
	}

	public void setSelectedOrders(List<OrderSelection> selectedOrders) {
		this.selectedOrders = selectedOrders;
	}
	
	public boolean isGuidance() {
		return guidance;
	}
	
	public void setGuidance(boolean guidance) {
		this.guidance = guidance;
	}
	
	public static class OrderSelection {
		private int orderId;
		private int deliveryId;
		private int unitsToConsider;
		
		//***** DEFUALT ACCESSORS *****//
		public int getOrderId() {
			return orderId;
		}
		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}
		public int getDeliveryId() {
			return deliveryId;
		}
		public void setDeliveryId(int deliveryId) {
			this.deliveryId = deliveryId;
		}
		public int getUnitsToConsider() {
			return unitsToConsider;
		}
		public void setUnitsToConsider(int unitsToConsider) {
			this.unitsToConsider = unitsToConsider;
		}
	}
	
}
