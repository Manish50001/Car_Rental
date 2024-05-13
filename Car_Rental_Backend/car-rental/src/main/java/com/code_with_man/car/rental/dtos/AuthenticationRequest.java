package com.code_with_man.car.rental.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
    private boolean isActive=true;


}
