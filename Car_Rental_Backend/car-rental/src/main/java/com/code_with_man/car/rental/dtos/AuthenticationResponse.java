package com.code_with_man.car.rental.dtos;


import com.code_with_man.car.rental.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;
    private Long userId;


}
