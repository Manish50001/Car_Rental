package com.code_with_man.car.rental.controllers;

import com.code_with_man.car.rental.dtos.AuthenticationRequest;
import com.code_with_man.car.rental.dtos.AuthenticationResponse;
import com.code_with_man.car.rental.dtos.SignupRequest;
import com.code_with_man.car.rental.entities.User;
import com.code_with_man.car.rental.repositories.UserRepository;
import com.code_with_man.car.rental.services.auth.AuthServiceImpl;
import com.code_with_man.car.rental.services.jwt.UserService;
import com.code_with_man.car.rental.services.jwt.UserServiceImpl;
import com.code_with_man.car.rental.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("api/auth")

public class AuthController {
	@Autowired
	private AuthServiceImpl authService;
	@Autowired
	private  AuthenticationManager authenticationManager;
	@Autowired
	private  UserService userService;
	@Autowired
	private  JwtUtils jwtUtils;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/signup")
	public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
		if (authService.hasUserWithEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>("user already exist with this email", HttpStatus.NOT_ACCEPTABLE);
		}
		User createdCustomerDto = authService.createUser(signupRequest);
		if (createdCustomerDto == null) {
			return new ResponseEntity<>("user not created", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	@ResponseBody
	public AuthenticationResponse authenticationResponse(@RequestBody AuthenticationRequest loginRequest)
			throws BadCredentialsException,

			DisabledException, UsernameNotFoundException {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect Username or Password");
		}

		final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(loginRequest.getEmail());
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

		final String jwt = jwtUtils.generateToken(userDetails);

		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		if (optionalUser.isPresent()) {

			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserId(optionalUser.get().getId());
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());

			return authenticationResponse;
		}

		return authenticationResponse;
	}
}
