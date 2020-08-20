package com.example.google.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	@Bean
	protected WebClient webClient(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository) {
		
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauthFilterFunction = 
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, auth2AuthorizedClientRepository) ;

		return WebClient.builder().apply(oauthFilterFunction.oauth2Configuration()).build();
	}
}
