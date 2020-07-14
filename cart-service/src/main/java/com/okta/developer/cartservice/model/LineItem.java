package com.okta.developer.cartservice.model;

import lombok.Data;

import javax.money.MonetaryAmount;
import javax.persistence.*;

@Entity
@Data
public class LineItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    private String productName;
    private Integer quantity;
    @Convert(converter=MonetaryAmountConverter.class)
    private MonetaryAmount price;

}