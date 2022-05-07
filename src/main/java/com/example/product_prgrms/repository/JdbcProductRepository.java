package com.example.product_prgrms.repository;

import com.example.product_prgrms.model.product.Product;
import com.example.product_prgrms.model.product.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private static final Logger logger= LoggerFactory.getLogger(JdbcProductRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcProductRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product insert(Product product) {
        jdbcTemplate.update("INSERT INTO products(product_name, stock, price, product_status, description)"
        +" values(:productName, :stock, :price, :productStatus, :description)", toParamMap(product));
        return product;
    }

    @Override
    public Product update(Product product) {
        jdbcTemplate.update("UPDATE products SET stock = :stock , description = :description, product_status = :productStatus"
        +" where product_id = :productId", toParamMap(product));
        return product;
    }


    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    @Override
    public Optional<Product> findById(long productId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM products where product_id = :productId", Collections.singletonMap("productId", productId), productRowMapper));
        }catch (EmptyResultDataAccessException e){
            logger.error("Got empty Product by id {}", productId);
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByProductStatus(ProductStatus status) {
        return jdbcTemplate.query("SELECT * FROM products where product_status = :productStatus", Collections.singletonMap("productStatus", status.toString()), productRowMapper);
    }


    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        long productId = resultSet.getLong("product_id");
        String productName = resultSet.getString("product_name");
        int stock = resultSet.getInt("stock");
        long price = resultSet.getLong("price");
        String description = resultSet.getString("description");
        String productStatus= resultSet.getString("product_status");

        return new Product(productId, productName, stock, price, description, ProductStatus.valueOf(productStatus));
    };

    private Map<String, Object> toParamMap(Product product) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("productId", product.getProductId());
        paramMap.put("productName", product.getProductName());
        paramMap.put("stock", product.getStock());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("productStatus", product.getProductStatus().toString());
        return paramMap;
    }

}
