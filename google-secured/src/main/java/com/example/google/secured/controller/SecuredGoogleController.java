package com.example.google.secured.controller;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredGoogleController {
	
	@GetMapping("/secured-google")
	public String secured(JwtAuthenticationToken token) {
		return "secured id " + token.getName() ;
	}
}
