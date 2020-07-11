package com.okta.developer.pricing.service;

import com.okta.developer.pricing.model.Cart;
import com.okta.developer.pricing.model.LineItem;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class DefaultPricingService implements PricingService {

    private final CurrencyUnit USD = Monetary.getCurrency("USD");

    @Override
    public Cart price(Cart cart) {


        MonetaryAmount total = Monetary.getDefaultAmountFactory()
                .setCurrency(USD)
                .setNumber(0)
                .create();

        for (LineItem li : cart.getLineItems()) {
            BigDecimal bigDecimal = new BigDecimal(Math.random() * 100)
                    .setScale(2, RoundingMode.HALF_UP);

            MonetaryAmount amount = Monetary.getDefaultAmountFactory()
                    .setCurrency(USD)
                    .setNumber(bigDecimal)
                    .create();
            li.setPrice(amount);
            total = total.add(amount.multiply(li.getQuantity()));
        }

        cart.setTotal(total);
        return cart;
    }
}