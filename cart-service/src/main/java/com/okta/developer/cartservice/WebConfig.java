package com.okta.developer.cartservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public MoneyModule moneyModule(){
        return new MoneyModule().withDefaultFormatting();
    }


    @Bean
    @LoadBalanced
    @Lazy
    protected RestTemplate restTemplate() {
        RestTemplate restTemplate =  new OAuth2RestTemplate(oAuthDetails());
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        restTemplate.getMessageConverters().add(0, converter);
        return restTemplate;
    }



    @Bean
    @ConfigurationProperties("pricing.oauth2.client")
    protected ClientCredentialsResourceDetails oAuthDetails() {
        return new ClientCredentialsResourceDetails();
    }


}