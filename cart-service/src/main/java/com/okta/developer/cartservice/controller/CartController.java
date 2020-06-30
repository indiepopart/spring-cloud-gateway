package com.okta.developer.cartservice.controller;

import com.okta.developer.cartservice.model.Cart;
import com.okta.developer.cartservice.repository.CartRepository;
import com.okta.developer.cartservice.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {


    @Autowired
    private CartRepository repository;

    @Autowired
    private PricingService pricingService;


    @GetMapping("/cart/{id}")
    public Cart getCart(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(() -> new CartNotFoundException("Cart not found:" + id));
    }

    @PostMapping("/cart")
    public Cart saveCart(@RequestBody  Cart cart){

        Cart priced = pricingService.price(cart);
        Cart saved = repository.save(priced);
        return saved;
    }
}