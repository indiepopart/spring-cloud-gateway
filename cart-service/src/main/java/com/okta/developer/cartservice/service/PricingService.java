package com.okta.developer.cartservice.service;

import com.okta.developer.cartservice.model.Cart;
import com.okta.developer.cartservice.model.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PricingService {

    @Autowired
    private RestTemplate restTemplate;

    public Cart price(Cart cart){
        try {
            HttpEntity httpEntity = new HttpEntity(cart);
            ResponseEntity<Cart> response = restTemplate
                    .exchange("http://pricing/pricing/price", HttpMethod.POST, httpEntity,
                            Cart.class);

            Cart priced = response.getBody();

            for (int i = 0; i < priced.getLineItems().size(); i++) {
                LineItem pricedLineItem = priced.getLineItems().get(i);
                LineItem lineItem = cart.getLineItems().get(i);
                lineItem.setPrice(pricedLineItem.getPrice());
            }

            cart.setTotal(priced.getTotal());


            return cart;
        } catch (Exception e){
            throw new PricingException(e);
        }
    }
}