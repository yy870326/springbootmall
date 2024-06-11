package com.zhe.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhe.mall.dao.OrderDao;
import com.zhe.mall.dao.ProductDao;
import com.zhe.mall.dto.BuyItem;
import com.zhe.mall.dto.CreateOrderRequest;
import com.zhe.mall.model.Order;
import com.zhe.mall.model.OrderItem;
import com.zhe.mall.model.Product;
import com.zhe.mall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Override
	public Order getOrderById(Integer orderId) {
		Order order = orderDao.getOrderById(orderId);

		List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

		order.setOrderItemList(orderItemList);

		return order;
	}

	@Transactional
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();

		for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());
			// 計算總價錢
			int amount = buyItem.getQuantity() * product.getPrice();
			totalAmount = totalAmount + amount;

			// 轉換 BuyItem to OrderItem
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(buyItem.getProductId());
			orderItem.setQuantity(buyItem.getQuantity());
			orderItem.setAmount(amount);

			orderItemList.add(orderItem);
		}
		// 創建訂單
		Integer orderId = orderDao.createOrder(userId, totalAmount);

		orderDao.createOrderItems(orderId, orderItemList);

		return orderId;
	}
}
