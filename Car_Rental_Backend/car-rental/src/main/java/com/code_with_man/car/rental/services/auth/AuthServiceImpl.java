package com.code_with_man.car.rental.services.auth;

import com.code_with_man.car.rental.dtos.AuthenticationRequest;
import com.code_with_man.car.rental.dtos.SignupRequest;
import com.code_with_man.car.rental.dtos.UserDto;
import com.code_with_man.car.rental.entities.User;
import com.code_with_man.car.rental.enums.UserRole;
import com.code_with_man.car.rental.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		if (adminAccount == null) {
			User newAdminUser = new User();
			newAdminUser.setName("Admin");
			newAdminUser.setEmail("admin@gmail.com");
			newAdminUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
			newAdminUser.setUserRole(UserRole.ADMIN);
			userRepository.save(newAdminUser);
			System.out.println("Admin User Created Successfully");
		}
	}

	@Override
	public User createUser(SignupRequest signupRequest) {

		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		User createdUser = userRepository.save(user);
		UserDto userDto = new UserDto();
		userDto.setId(createdUser.getId());
		return createdUser;

	}

	@Override
	public boolean hasUserWithEmail(String email) {

		return userRepository.findFirstByEmail(email).isPresent();
	}

	@Override
	public boolean loginUser(AuthenticationRequest loginRequest) {
		Optional<User> existingUser = userRepository.findFirstByEmail(loginRequest.getEmail());
		if (existingUser == null) {
			return false;
		}
		return true;
	}
}
