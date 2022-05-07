package com.example.product_prgrms.repository;

import com.example.product_prgrms.model.product.Product;
import com.example.product_prgrms.model.product.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product insert(Product product);

    Product update(Product product);

    List<Product> findAll();

    Optional<Product> findById(long id);

    List<Product> findByProductStatus(ProductStatus status);

}
