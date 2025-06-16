package com.example.springsecurityjwt.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {

    @GetMapping("/ex/health-check")
    //@PreAuthorize("hasAuthority('READ')")
    public String hello() {
        log.info("HEALTH CHECK NO SECURED");
        return "Health check not secured";
    }

    @GetMapping("/health-check-secured")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String helloSecured() {
        log.info("HEALTH CHECK SECURED");
        return "Health check secured";
    }
}
