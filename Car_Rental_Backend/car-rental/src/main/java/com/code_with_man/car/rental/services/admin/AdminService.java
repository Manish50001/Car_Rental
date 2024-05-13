package com.code_with_man.car.rental.services.admin;

import com.code_with_man.car.rental.dtos.BookACarDto;
import com.code_with_man.car.rental.dtos.CarDto;
import com.code_with_man.car.rental.dtos.CarDtoListDto;
import com.code_with_man.car.rental.dtos.SearchCarDto;
import com.code_with_man.car.rental.entities.Car;

import java.io.IOException;
import java.util.List;

public interface AdminService {
     boolean postCar(CarDto carDto) throws IOException;
     List<CarDto> getAllCars();
     void deleteCar(Long id);

     CarDto getCarById(Long id);

     boolean updateCar(Long carId, CarDto carDto) throws IOException;

     List<BookACarDto> getBookings();

     boolean changeBookingStatus(Long bookingId,String status);

     CarDtoListDto searchCar(SearchCarDto searchCarDto);


}
