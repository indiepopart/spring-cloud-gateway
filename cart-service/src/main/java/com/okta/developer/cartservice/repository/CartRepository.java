package com.okta.developer.cartservice.repository;

import com.okta.developer.cartservice.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {
}