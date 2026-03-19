package com.dandan.kc.dandan.dto;

import lombok.Data;

@Data
public class StaffOnboardRequest {
    private String email;
    private String name;
    private String merchantId;
    private String role;
}