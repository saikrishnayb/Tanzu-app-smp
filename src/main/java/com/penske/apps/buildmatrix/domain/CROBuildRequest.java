package com.penske.apps.buildmatrix.domain;

public class CROBuildRequest {
	int croBuildRequestId;
	int runId;
	int orderId;
	int deliveryId;
	int requestedQty;
	int fulfilledQty;

	public int getCroBuildRequestId() {
		return croBuildRequestId;
	}

	public int getRunId() {
		return runId;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getDeliveryId() {
		return deliveryId;
	}

	public int getRequestedQty() {
		return requestedQty;
	}

	public int getFulfilledQty() {
		return fulfilledQty;
	}

	public void setCroBuildRequestId(int croBuildRequestId) {
		this.croBuildRequestId = croBuildRequestId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}

	public void setRequestedQty(int requestedQty) {
		this.requestedQty = requestedQty;
	}

	public void setFulfilledQty(int fulfilledQty) {
		this.fulfilledQty = fulfilledQty;
	}

}
