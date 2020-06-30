package com.okta.developer.cartservice.model;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.persistence.AttributeConverter;
import java.math.BigDecimal;

public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, BigDecimal> {

    private final CurrencyUnit USD = Monetary.getCurrency("USD");


    @Override
    public BigDecimal convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        if (monetaryAmount == null){
            return null;
        }
        return monetaryAmount.getNumber().numberValue(BigDecimal.class);
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(BigDecimal bigDecimal) {
        if (bigDecimal == null){
            return null;
        }
        return Monetary.getDefaultAmountFactory()
                .setCurrency(USD)
                .setNumber(bigDecimal.doubleValue())
                .create();
    }
}