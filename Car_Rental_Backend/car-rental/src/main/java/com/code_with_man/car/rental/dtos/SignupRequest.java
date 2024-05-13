package com.code_with_man.car.rental.dtos;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;
    private String password;
    private String name;

}
