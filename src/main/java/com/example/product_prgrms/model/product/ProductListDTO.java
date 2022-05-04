package com.example.product_prgrms.model.product;

import lombok.Getter;

@Getter
public class ProductListDTO {
    final long id;
    final String productName;
    final int stock;
    final long price;
    final String description;

    final String productStatus;

    public ProductListDTO(long id, String productName, int stock, long price, String description, String productStatus) {
        this.id = id;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.description = description;
        this.productStatus = productStatus;
    }
}
