package com.zhe.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhe.mall.constant.ProductCategory;
import com.zhe.mall.dto.ProductQueryParams;
import com.zhe.mall.dto.ProductRequest;
import com.zhe.mall.model.Product;
import com.zhe.mall.service.ProductService;
import com.zhe.mall.util.Page;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>>  getProducts(
			// 查詢條件 Filtering
			@RequestParam(required = false) ProductCategory productCategory,
			@RequestParam(required = false)	String search,
			
			// 排序 Sorting
			@RequestParam(defaultValue = "created_date") String orderBy,
			@RequestParam(defaultValue = "desc") String sort,
			
			 // 分頁 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
			
			) {
		
		ProductQueryParams params = new ProductQueryParams();
		params.setCategory(productCategory);
		params.setSearch(search);
		params.setOrderBy(orderBy);
		params.setSort(sort);
		params.setLimit(limit);
		params.setOffset(offset);
		List<Product> productList =  productService.getProducts(params);
		// 取得 product 總數
        Integer total = productService.countProduct(params);

        // 分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
	}
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
		Product product = productService.getProductById(productId);

		if (product != null) {
			System.out.println("200");
			return ResponseEntity.status(HttpStatus.OK).body(product);
		} else {
			System.out.println("404");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
		Integer id = productService.createProduct(productRequest);
		Product product = productService.getProductById(id);

		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id,
			@RequestBody @Valid ProductRequest productRequest) {
		// 檢查 product 是否存在
		Product product = productService.getProductById(id);

		if (product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		// 修改商品
		productService.updateProduct(id, productRequest);
		Product updateProduct = productService.getProductById(id);

		return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
		productService.deleteProductById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
