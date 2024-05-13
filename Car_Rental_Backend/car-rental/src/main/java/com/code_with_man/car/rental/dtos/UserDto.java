package com.code_with_man.car.rental.dtos;

import com.code_with_man.car.rental.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private UserRole userRole;

}
