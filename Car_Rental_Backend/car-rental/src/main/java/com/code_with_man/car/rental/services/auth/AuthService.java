package com.code_with_man.car.rental.services.auth;

import com.code_with_man.car.rental.dtos.AuthenticationRequest;
import com.code_with_man.car.rental.dtos.SignupRequest;
import com.code_with_man.car.rental.entities.User;

public interface AuthService {


    User createUser(SignupRequest signupRequest);
    boolean hasUserWithEmail(String email);
    boolean loginUser(AuthenticationRequest loginRequest);
}
