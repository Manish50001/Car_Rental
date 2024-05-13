package com.code_with_man.car.rental.repositories;

import com.code_with_man.car.rental.entities.User;
import com.code_with_man.car.rental.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail (String email);

    User findByUserRole (UserRole userRole);
}
