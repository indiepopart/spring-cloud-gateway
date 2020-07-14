package com.okta.developer.cartservice.service;

import com.okta.developer.cartservice.model.Cart;
import com.okta.developer.cartservice.model.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PricingService {


    @Autowired
    private WebClient webClient;

    public Cart price(Cart cart){
        try {

            Mono<Cart> response = webClient
                    .post()
                    .bodyValue(cart)
                    .retrieve().bodyToMono(Cart.class);

            Cart priced = response.block();

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