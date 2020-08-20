package com.example.google.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class GoogleController {
	
	@Autowired
	private WebClient webClient ;
	
	@GetMapping("/")
	public String index(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
			@AuthenticationPrincipal OAuth2User oauth2User) {
		return "id is " + oauth2User.getName() ;
	}
	
	@GetMapping("/google")
	public String google(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
			@AuthenticationPrincipal OAuth2User oauth2User) {
		
		return this.webClient
				.get()
				.uri("http://localhost:8081/secured-google")
				.attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
				.retrieve()
				.bodyToMono(String.class)
				.block() ;
	}
	
	@GetMapping("/google-ok")
	public String googleOk(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
			@AuthenticationPrincipal OAuth2User oauth2User) {
		String accessToken = ((OidcUser) oauth2User).getIdToken().getTokenValue() ;
		
		return this.webClient
				.get()
				.uri("http://localhost:8081/secured-google")
				.headers(header -> header.setBearerAuth(accessToken))
				.retrieve()
				.bodyToMono(String.class)
				.block() ;
	}
}
