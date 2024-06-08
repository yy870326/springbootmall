package com.zhe.mall.service;

import java.util.List;


import com.zhe.mall.constant.ProductCategory;
import com.zhe.mall.model.Product;

import dto.ProductQueryParams;
import dto.ProductRequest;

public interface ProductService {
	List<Product> getProducts(ProductQueryParams params);

	Product getProductById(Integer productId);

	Integer createProduct(ProductRequest productRequest);

	void updateProduct(Integer id, ProductRequest productRequest);

	void deleteProductById(Integer id);
	
	Integer countProduct(ProductQueryParams params);
}
