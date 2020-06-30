package com.okta.developer.cartservice.model;

import javax.money.MonetaryAmount;
import javax.persistence.*;

@Entity
public class LineItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    private String productName;
    private Integer quantity;
    @Convert(converter=MonetaryAmountConverter.class)
    private MonetaryAmount price;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}