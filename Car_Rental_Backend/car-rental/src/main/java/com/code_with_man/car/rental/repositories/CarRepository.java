package com.code_with_man.car.rental.repositories;

import com.code_with_man.car.rental.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

}
