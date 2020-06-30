package com.okta.developer.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@SpringBootTest
class SpringCloudGatewayApplicationTests {

	@MockBean
	private ReactiveClientRegistrationRepository clientRegistrationRepository;

	@MockBean
	private ReactiveJwtDecoder reactiveJwtDecoder;

	@MockBean
	private ServerOAuth2AuthorizedClientRepository serverOAuth2AuthorizedClientRepository;

	@Test
	void contextLoads() {
	}

}
