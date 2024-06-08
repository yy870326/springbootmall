package com.zhe.mall.service;

import java.util.List;


import com.zhe.mall.constant.ProductCategory;
import com.zhe.mall.dto.ProductQueryParams;
import com.zhe.mall.dto.ProductRequest;
import com.zhe.mall.model.Product;

public interface ProductService {
	List<Product> getProducts(ProductQueryParams params);

	Product getProductById(Integer productId);

	Integer createProduct(ProductRequest productRequest);

	void updateProduct(Integer id, ProductRequest productRequest);

	void deleteProductById(Integer id);
	
	Integer countProduct(ProductQueryParams params);
}
