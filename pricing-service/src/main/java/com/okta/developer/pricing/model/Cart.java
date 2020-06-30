package com.okta.developer.pricing.model;

import lombok.Data;
import javax.money.MonetaryAmount;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {


    private Integer id;
    private String customerId;
    private List<LineItem> lineItems = new ArrayList<>();
    private MonetaryAmount total;

    public void addLineItem(LineItem lineItem){
        this.lineItems.add(lineItem);
    }
}