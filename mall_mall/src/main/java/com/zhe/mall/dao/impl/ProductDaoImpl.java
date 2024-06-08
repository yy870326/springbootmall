package com.zhe.mall.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.zhe.mall.constant.ProductCategory;
import com.zhe.mall.dao.ProductDao;
import com.zhe.mall.model.Product;
import com.zhe.mall.rowmapper.ProductRowMapper;

import dto.ProductQueryParams;
import dto.ProductRequest;

@Validated
@Component
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void checkDatabaseConnection() {
		try {
			jdbcTemplate.execute("SELECT 1");
			System.out.println("Database connection successful");
		} catch (Exception e) {
			System.err.println("Error connecting to database: " + e.getMessage());
		}
	}
	
	 @Override
	    public Integer countProduct(ProductQueryParams productQueryParams) {
	        String sql = "SELECT count(*) FROM product WHERE 1=1";

	        Map<String, Object> map = new HashMap<>();

	        // 查詢條件
	        sql = addFilteringSql(sql, map, productQueryParams);

	        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

	        return total;
	    }

	@Override
	public List<Product> getProducts(ProductQueryParams params) {
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, "
				+ "created_date, last_modified_date " + "FROM product WHERE 1=1 ";

		Map<String, Object> map = new HashMap<>();
		
		sql = addFilteringSql(sql, map, params);
//		if (params.getCategory() != null) {
//			sql = sql +" and category = :category";
//			map.put("category", params.getCategory().name());
//		}
//		if (params.getSearch() != null) {
//			sql = sql +" and product_name Like :search";
//			map.put("search", "%"+params.getSearch()+"%");
//		}
		 // 排序
		sql = " order by "+params.getOrderBy()+" " + params.getSort();
		
		// 分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", params.getLimit());
        map.put("offset", params.getOffset());
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

		return productList;
	}

	@Override
	public Product getProductById(Integer productId) {
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, "
				+ "created_date, last_modified_date " + "FROM mall.product WHERE product_id = :productId";

		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);

		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
		System.out.println(productId);

		checkDatabaseConnection();
		if (productList.size() > 0) {
			return productList.get(0);
		} else {
			return null;
		}

	}

	@Override
	public Integer createProduct(ProductRequest productRequest) {
		String sql = "INSERT INTO product(product_name, category, image_url, price, stock, "
				+ "description, created_date, last_modified_date) "
				+ "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, "
				+ ":createdDate, :lastModifiedDate)";

		Map<String, Object> map = new HashMap<>();
		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().toString());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());

		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

		int productId = keyHolder.getKey().intValue();

		return productId;
	}

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, "
				+ "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate"
				+ " WHERE product_id = :productId ";

		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);

		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().toString());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());

		map.put("lastModifiedDate", new Date());

		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void deleteProductById(Integer id) {
		String sql = "delete from product WHERE product_id = :productId ";

		Map<String, Object> map = new HashMap<>();
		map.put("productId", id);

		namedParameterJdbcTemplate.update(sql, map);
	}
	
	   private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
	        if (productQueryParams.getCategory() != null) {
	            sql = sql + " AND category = :category";
	            map.put("category", productQueryParams.getCategory().name());
	        }

	        if (productQueryParams.getSearch() != null) {
	            sql = sql + " AND product_name LIKE :search";
	            map.put("search", "%" + productQueryParams.getSearch() + "%");
	        }

	        return sql;
	    }

}
