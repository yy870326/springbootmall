package com.zhe.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhe.mall.constant.ProductCategory;
import com.zhe.mall.dao.ProductDao;
import com.zhe.mall.model.Product;
import com.zhe.mall.service.ProductService;

import dto.ProductQueryParams;
import dto.ProductRequest;

@Component
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	@Override
	public List<Product> getProducts(ProductQueryParams params ) {
		return productDao.getProducts(params);
	}
	@Override
	public Product getProductById(Integer productId) {
		
		return productDao.getProductById(productId);
	}
	@Override
	public Integer createProduct(ProductRequest productRequest) {
		return productDao.createProduct(productRequest);
	}
	@Override
	public void updateProduct(Integer id, ProductRequest productRequest) {
		productDao.updateProduct(id, productRequest);
		
	}
	@Override
	public void deleteProductById(Integer id) {
		productDao.deleteProductById(id);		
	}
	@Override
	public Integer countProduct(ProductQueryParams params) {

		return productDao.countProduct( params);
	}
	

}
