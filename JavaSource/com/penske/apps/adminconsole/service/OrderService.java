package com.penske.apps.adminconsole.service;

import java.util.List;

import com.penske.apps.adminconsole.model.Order;

public interface OrderService {
	public List<Order> getAllApprovedOrders();
}
