package com.dandan.kc.dandan.dto;

import lombok.Data;

@Data
public class CustomerOnboardRequest {

    private String email;
    private String name;
    private String phone;
    private String merchantId;
}