package com.okta.developer.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class OktaOAuth2WebSecurity {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        http.authorizeExchange()
                .anyExchange()
                .authenticated()
                .and().oauth2Login()
                .and().csrf().disable()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}