package com.penske.apps.buildmatrix.domain;

public class CroOrderKey {
	
	private int orderId;
	private int deliveryId;
	
	protected CroOrderKey(){}
	
	public CroOrderKey(int orderId, int deliveryId)
	{
		this.orderId = orderId;
		this.deliveryId = deliveryId;
	}

	
	public CroOrderKey(ApprovedOrder order) {
		this.orderId = order.getOrderId();
		this.deliveryId = order.getDeliveryId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + deliveryId;
		result = prime * result + orderId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CroOrderKey other = (CroOrderKey) obj;
		if (deliveryId != other.deliveryId)
			return false;
		if (orderId != other.orderId)
			return false;
		return true;
	}

	//***** DEFAULT ACCESSORS *****//
	public int getOrderId() {
		return orderId;
	}

	public int getDeliveryId() {
		return deliveryId;
	}
	
}
