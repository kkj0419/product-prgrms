package com.example.product_prgrms.model;

import lombok.Getter;

@Getter
public class Product {

    private long product_id;

    private String productName;
    private int stock =0;
    private long price;
    private String description;

    public Product(long product_id, String productName, int stock, long price, String description) {
        this.product_id = product_id;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.description = description;
    }

    public Product(String productName, int stock, long price, String description) {
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.description = description;
    }

    public void changeStock(int stock){
        if (stock >= 0) {
            this.stock=stock;
        }else{
            //TODO 재고입력예외
        }
    }

    //TODO 여유 있을 때 수정 기능 추가
    public void changeDescription(String description) {

    }



}
