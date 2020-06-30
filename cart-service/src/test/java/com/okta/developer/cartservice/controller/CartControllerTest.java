package com.okta.developer.cartservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.developer.cartservice.model.Cart;
import com.okta.developer.cartservice.model.LineItem;
import com.okta.developer.cartservice.repository.CartRepository;
import com.okta.developer.cartservice.service.PricingService;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.money.Monetary;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PricingService pricingService;


    @Test
    void testSaveCart() throws Exception {

        Cart cart = new Cart();
        LineItem lineItem = new LineItem();
        lineItem.setQuantity(1);
        lineItem.setId(1);
        lineItem.setPrice(Money.of(10, Monetary.getCurrency("USD")));
        cart.addLineItem(lineItem);
        cart.setTotal(Money.of(10, Monetary.getCurrency("USD")));
        cart.setId(1);

        given(this.cartRepository.save(any(Cart.class))).willReturn(cart);
        given(this.pricingService.price(any(Cart.class))).willReturn(cart);


        this.mvc.perform(post("/cart").accept(MediaType.APPLICATION_JSON)
                .with(jwt())
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("total.formatted").value("USD10.00"));

    }

    @Test
    void testSaveUnauthorized() throws Exception {

        Cart cart = new Cart();
        LineItem lineItem = new LineItem();
        lineItem.setQuantity(1);
        lineItem.setId(1);
        lineItem.setPrice(Money.of(10, Monetary.getCurrency("USD")));
        cart.addLineItem(lineItem);
        cart.setTotal(Money.of(10, Monetary.getCurrency("USD")));
        cart.setId(1);


        this.mvc.perform(post("/cart").accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void testGetUnauthorized() throws Exception {


        this.mvc.perform(get("/cart/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void testGet() throws Exception {

        Cart cart = new Cart();
        LineItem lineItem = new LineItem();
        lineItem.setQuantity(1);
        lineItem.setId(1);
        lineItem.setPrice(Money.of(10, Monetary.getCurrency("USD")));
        cart.addLineItem(lineItem);
        cart.setTotal(Money.of(10, Monetary.getCurrency("USD")));
        cart.setId(1);

        given(this.cartRepository.findById(any(Integer.class))).willReturn(Optional.of(cart));


        this.mvc.perform(get("/cart/1").accept(MediaType.APPLICATION_JSON)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("total.formatted").value("USD10.00"));


    }
}
