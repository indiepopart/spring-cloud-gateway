package com.okta.developer.pricing;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public MoneyModule moneyModule(){
        return new MoneyModule().withDefaultFormatting();
    }

}