package com.okta.developer.pricing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.developer.pricing.model.Cart;
import com.okta.developer.pricing.model.LineItem;
import com.okta.developer.pricing.service.PricingService;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import javax.money.Monetary;
import javax.ws.rs.core.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PricingController.class)
public class PricingControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private PricingService pricingService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testPrice() throws Exception {

        Cart priced = new Cart();
        LineItem lineItem = new LineItem();
        lineItem.setQuantity(1);
        lineItem.setId(1);
        lineItem.setPrice(Money.of(10, Monetary.getCurrency("USD")));
        priced.addLineItem(lineItem);
        priced.setTotal(Money.of(10, Monetary.getCurrency("USD")));

        given(this.pricingService.price(any(Cart.class))).willReturn(priced);


        Cart cart = new Cart();
        cart.addLineItem(new LineItem(1, 2));


        this.mvc.perform(post("/pricing/price").accept(MediaType.APPLICATION_JSON)
                .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_pricing")))
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("total.formatted").value("USD10.00"));

    }

    @Test
    void testUnauthorized() throws Exception {

        Cart cart = new Cart();
        cart.addLineItem(new LineItem(1, 2));


        this.mvc.perform(post("/pricing/price").accept(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(objectMapper.writeValueAsString(cart))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

}
