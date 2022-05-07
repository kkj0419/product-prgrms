package com.example.product_prgrms.model.product;

import lombok.Getter;

@Getter
public class ProductListDTO {
    final long productId;
    final String productName;
    final int stock;
    final long price;
    final String description;

    final String productStatus;

    public ProductListDTO(long productId, String productName, int stock, long price, String description, String productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.description = description;
        this.productStatus = productStatus;
    }
}
