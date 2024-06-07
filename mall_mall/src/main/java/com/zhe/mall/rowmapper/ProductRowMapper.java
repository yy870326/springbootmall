package com.zhe.mall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zhe.mall.constant.ProductCategory;
import com.zhe.mall.model.Product;

public class ProductRowMapper implements RowMapper<Product> {

	   @Override
	    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
	        Product product = new Product();

	        product.setProductId(resultSet.getInt("product_id"));
	        product.setProductName(resultSet.getString("product_name"));
	        
//	        String category = resultSet.getString("category");
//	        ProductCategory productCategory = ProductCategory.valueOf(category);
	        product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));
	        product.setImageUrl(resultSet.getString("image_url"));
	        product.setPrice(resultSet.getInt("price"));
	        product.setStock(resultSet.getInt("stock"));
	        product.setDescription(resultSet.getString("description"));
	        product.setCreatedDate(resultSet.getTimestamp("created_date"));
	        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

	        return product;
	    }

}
