package com.dandan.kc.dandan.dto;

import lombok.Data;

@Data
public class MerchantOnboardRequest {
    private String merchantName;
    private String email;
    private String phone;
}