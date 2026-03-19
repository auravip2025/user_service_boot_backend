package com.dandan.kc.dandan.controller;

import com.dandan.kc.dandan.dto.CustomerOnboardRequest;
import com.dandan.kc.dandan.service.KeycloakProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final KeycloakProvisioningService service;

    @PostMapping("/onboard")
    public String onboardCustomer(@RequestBody CustomerOnboardRequest request) {
        return service.onboardCustomer(request);
    }
}