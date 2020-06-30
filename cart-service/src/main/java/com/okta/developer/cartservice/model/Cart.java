package com.okta.developer.cartservice.model;


import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String customerId;
    @Convert(converter=MonetaryAmountConverter.class)
    private MonetaryAmount total;

    @OneToMany(cascade = CascadeType.ALL)
    private List<LineItem> lineItems = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public MonetaryAmount getTotal() {
        return total;
    }

    public void setTotal(MonetaryAmount total) {
        this.total = total;
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }
}