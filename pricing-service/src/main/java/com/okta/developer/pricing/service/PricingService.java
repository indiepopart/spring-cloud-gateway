package com.okta.developer.pricing.service;

import com.okta.developer.pricing.model.Cart;

public interface PricingService {

    Cart price(Cart cart);
}
