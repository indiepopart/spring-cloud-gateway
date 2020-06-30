package com.okta.developer.pricing.controller;

import com.okta.developer.pricing.model.Cart;
import com.okta.developer.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @PostMapping("/pricing/price")
    public Cart price(@RequestBody Cart cart){

        Cart priced = pricingService.price(cart);
        return priced;
    }

}