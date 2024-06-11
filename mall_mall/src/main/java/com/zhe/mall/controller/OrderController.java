package com.zhe.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhe.mall.dto.CreateOrderRequest;
import com.zhe.mall.model.Order;
import com.zhe.mall.service.OrderService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	
	@PostMapping("/users/{id}/orders")
	public ResponseEntity<?> createOrder(
			@PathVariable Integer userId,
			@RequestBody @Valid CreateOrderRequest createOrderRequest
			){
		Integer orderID=orderService.createOrder(userId,createOrderRequest);
		
		Order order = orderService.getOrderById(orderID);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
		
	}
}
