package com.zhe.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhe.mall.dao.ProductDao;
import com.zhe.mall.model.Product;
import com.zhe.mall.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	@Override
	public Product getProductById(Integer productId) {
		
		return productDao.getProductById(productId);
	}

}
