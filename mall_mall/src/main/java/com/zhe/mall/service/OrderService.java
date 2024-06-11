package com.zhe.mall.service;

import com.zhe.mall.dto.CreateOrderRequest;
import com.zhe.mall.model.Order;

public interface OrderService {
	Order getOrderById(Integer orderId);

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
