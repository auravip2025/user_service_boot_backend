package com.dandan.kc.dandan.controller;

import com.dandan.kc.dandan.dto.StaffOnboardRequest;
import com.dandan.kc.dandan.service.KeycloakProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final KeycloakProvisioningService service;

    @PostMapping("/onboard")
    public String onboardStaff(@RequestBody StaffOnboardRequest request) {
        return service.onboardStaff(request);
    }
}