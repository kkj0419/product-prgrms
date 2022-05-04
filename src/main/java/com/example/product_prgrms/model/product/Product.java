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
            //TODO 재고입력예외
            throw new InvalidProductStockException();
        }
    }

    //TODO 여유 있을 때 수정 기능 추가
    public void changeDescription(String description) {
        this.description = description;
    }

    public static ProductListDTO toListDTO(Product product) {
        return new ProductListDTO(product.getProductId(), product.getProductName(), product.getStock(), product.getPrice(), product.getDescription(), product.getProductStatus().toString());
    }

}
