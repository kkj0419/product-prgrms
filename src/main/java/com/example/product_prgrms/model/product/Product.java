package com.example.product_prgrms.model.product;

import com.example.product_prgrms.exception.InvalidProductStockException;
import lombok.Getter;

@Getter
public class Product {

    private long productId;

    private String productName;
    private int stock = 0;
    private long price;
    private String description;

    private ProductStatus productStatus;

    public Product(long productId, String productName, int stock, long price, String description, ProductStatus productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.description = description;
        this.productStatus = productStatus;
    }

    public Product(String productName, int stock, long price, String description, ProductStatus productStatus) {
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.description = description;
        this.productStatus = productStatus;
    }

    public void changeStock(int stock) throws InvalidProductStockException {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            throw new InvalidProductStockException();
        }
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public ProductListDTO toListDTO() {
        return new ProductListDTO(productId, productName, stock, price, description, productStatus.toString());
    }

}
