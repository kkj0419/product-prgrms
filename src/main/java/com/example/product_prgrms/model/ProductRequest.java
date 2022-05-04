package com.example.product_prgrms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRequest {

    public String productName;
    public int stock;

    public long price;

    public String description;

    public String productStatus;

    public ProductRequest(){}

    public ProductRequest(String productName, int stock, long price, String description, String productStatus) {
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.description = description;
        this.productStatus = productStatus;
    }

    public Product toEntity() {
        return new Product(productName, stock, price, description, ProductStatus.valueOf(productStatus));
    }
}