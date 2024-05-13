package com.code_with_man.car.rental.repositories;

import com.code_with_man.car.rental.dtos.BookACarDto;
import com.code_with_man.car.rental.entities.BookACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookACarRepository extends JpaRepository<BookACar,Long> {

    List<BookACar> findAllByUserId (Long userId);
}
