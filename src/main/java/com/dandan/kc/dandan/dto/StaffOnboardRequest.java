package com.dandan.kc.dandan.dto;

import lombok.Data;

@Data
public class StaffOnboardRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String merchantId;
    private String role;
}