package com.penske.apps.adminconsole.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.penske.apps.adminconsole.model.Order;

@Service
public class DefaultOrderService implements OrderService{
	
	static List<Order> approvedOrders=new ArrayList<Order>();
	
	public List<Order> getAllApprovedOrders() {
		List<Order> approvedOrders=getAllApprovedOrdersMockDataService();
		return approvedOrders;
	}
	
	//Mock service methods
	public List<Order> getAllApprovedOrdersMockDataService() {
		approvedOrders.clear();
		Order order1=new Order(16714,"Approved","0834","0602","0666-10","READING","CDL Dry Van",25);
		Order order2=new Order(16715,"Approved","0834","0602","0426-10","LANCASTER","CDL Dry Van",9);
		Order order3=new Order(16716,"Approved","0834","0602","0428-10","LANDOVER","CDL Dry Van",4);
		Order order4=new Order(16717,"Approved","0834","0602","0525-10","HANGERSTOWN","CDL Dry Van",5);
		approvedOrders.add(order1);
		approvedOrders.add(order2);
		approvedOrders.add(order3);
		approvedOrders.add(order4);
		return approvedOrders;
	}
}
