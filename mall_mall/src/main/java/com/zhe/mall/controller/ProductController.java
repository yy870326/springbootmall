package com.zhe.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zhe.mall.model.Product;
import com.zhe.mall.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
		Product product = productService.getProductById(productId);
		
		if (product != null) {
			System.out.println("200");
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}else {
			System.out.println("404");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
	}

}
