package com.zhe.mall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.zhe.mall.dao.ProductDao;
import com.zhe.mall.model.Product;
import com.zhe.mall.rowmapper.ProductRowMapper;

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

}
