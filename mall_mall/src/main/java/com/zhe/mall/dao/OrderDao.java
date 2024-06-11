package com.zhe.mall.dao;

import java.util.List;

import com.zhe.mall.model.Order;
import com.zhe.mall.model.OrderItem;

public interface OrderDao {
	List<OrderItem> getOrderItemsByOrderId(Integer orderId);

	Order getOrderById(Integer orderId);

	Integer createOrder(Integer userId, Integer amount);

	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
