package com.example.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@Slf4j
public class TestController {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/test/logged")
    public String sayHello(@AuthenticationPrincipal Jwt principal) {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");

        return "Hello " + (principal != null ? principal.getSubject() : "anonymous");
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/test/error")
    public String throw501Error() {
        throw new UnsupportedOperationException();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/test/denied")
    public String throw403Error() {
        throw new AccessDeniedException("You can't do this task");
    }
}
