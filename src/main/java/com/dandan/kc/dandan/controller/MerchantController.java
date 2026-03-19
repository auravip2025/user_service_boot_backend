package com.dandan.kc.dandan.controller;

import com.dandan.kc.dandan.dto.MerchantOnboardRequest;
import com.dandan.kc.dandan.service.KeycloakProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final KeycloakProvisioningService service;

    @PostMapping("/onboard")
    public String onboardMerchant(@RequestBody MerchantOnboardRequest request) {
        return service.onboardMerchant(request);
    }
}