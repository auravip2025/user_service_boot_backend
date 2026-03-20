package com.dandan.kc.dandan.dto;

import lombok.Data;

@Data
public class CustomerOnboardRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String merchantId;
}