package com.penske.apps.buildmatrix.model;

import java.util.List;

import com.penske.apps.buildmatrix.domain.CroOrderKey;
import static java.util.stream.Collectors.toList;

public class OrderSelectionForm {
	
	private Integer buildId;
	private List<OrderSelection> selectedOrders;

	//***** MODIFIED ACCESSORS *****//
	public List<CroOrderKey> getCroOrderKeys(){
		List<CroOrderKey> croOrderKeys = selectedOrders.stream().map(so -> new CroOrderKey(so.getOrderId(), so.getDeliveryId())).collect(toList());
		return croOrderKeys;
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
	
	public static class OrderSelection {
		private int orderId;
		private int deliveryId;
		
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
		
	}
	
}
