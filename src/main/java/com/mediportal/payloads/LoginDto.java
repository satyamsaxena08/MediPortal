package com.mediportal.payloads;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
