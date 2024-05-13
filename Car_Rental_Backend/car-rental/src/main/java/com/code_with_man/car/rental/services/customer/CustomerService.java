package com.code_with_man.car.rental.services.customer;

import com.code_with_man.car.rental.dtos.BookACarDto;
import com.code_with_man.car.rental.dtos.CarDto;
import com.code_with_man.car.rental.dtos.CarDtoListDto;
import com.code_with_man.car.rental.dtos.SearchCarDto;

import java.util.List;

public interface CustomerService {

    List<CarDto> getAllCars();
    boolean bookACar(BookACarDto bookACarDto);
    CarDto getCarById(Long carId);
    List<BookACarDto> getBookingsByUserId(Long userId);

    CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
