package com.okta.developer.pricing.model;

import lombok.Data;
import javax.money.MonetaryAmount;

@Data
public class LineItem {

    private Integer id;
    private Integer quantity;
    private MonetaryAmount price;
    private String productName;

    public LineItem(){
    }

    public LineItem(Integer id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
