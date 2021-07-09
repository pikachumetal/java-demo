package com.example.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    // Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping("/")
    public String sayHello(@AuthenticationPrincipal Jwt principal) {
        return "Hello " + (principal != null ? principal.getSubject() : "anonymous");
    }
}
