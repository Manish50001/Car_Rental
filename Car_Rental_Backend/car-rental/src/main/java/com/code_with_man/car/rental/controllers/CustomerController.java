package com.code_with_man.car.rental.controllers;

import com.code_with_man.car.rental.dtos.BookACarDto;
import com.code_with_man.car.rental.dtos.CarDto;
import com.code_with_man.car.rental.dtos.SearchCarDto;
import com.code_with_man.car.rental.services.customer.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor

public class CustomerController {
    @Autowired
    private  CustomerService customerService;
    @GetMapping("/cars")
    public ResponseEntity<List> getAllCars () {
        List<CarDto> carDtoList = customerService.getAllCars();
        return ResponseEntity.ok(carDtoList);
    }

    @PostMapping("/car/book/{carId}")
    public ResponseEntity<Void> bookACar(@RequestBody BookACarDto bookACarDto) {

        boolean success = customerService.bookACar(bookACarDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> getCarById (@PathVariable Long carId) {
        CarDto carDto = customerService.getCarById(carId);
        if (carDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(carDto);
    }
    @GetMapping("/car/bookings/{userId}")
    public ResponseEntity<List<BookACarDto>> getBookingsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }

    @PostMapping("/car/search")
    public  ResponseEntity<?> searching (@RequestBody SearchCarDto searchCarDto){

        return ResponseEntity.ok(customerService.searchCar(searchCarDto));
    }
}
