package com.code_with_man.car.rental.controllers;

import com.code_with_man.car.rental.dtos.BookACarDto;
import com.code_with_man.car.rental.dtos.CarDto;
import com.code_with_man.car.rental.dtos.SearchCarDto;
import com.code_with_man.car.rental.services.admin.AdminService;
import com.code_with_man.car.rental.services.admin.AdminServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
     @Autowired
	private  AdminServiceImpl adminService;

	@PostMapping("/car")
	public ResponseEntity<?> postCar(@ModelAttribute CarDto carDto) throws IOException {
		boolean success = adminService.postCar(carDto);
		System.out.println("inside controller");
			if (success) {
			System.out.println("inside success controller");
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			System.out.println("inside else controller");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@GetMapping("/cars")
     public ResponseEntity<?>getAllCars(){
		return ResponseEntity.ok(adminService.getAllCars());
	 }
@DeleteMapping("/car/{id}")
	 public ResponseEntity<Void>deleteCar(@PathVariable Long id){
        adminService.deleteCar(id);
		return ResponseEntity.ok(null);
	 }
@GetMapping("/car/{id}")
	 public ResponseEntity<CarDto> getCarById(@PathVariable Long id){
		CarDto carDto=adminService.getCarById(id);
		return ResponseEntity.ok(carDto);

	 }
 @PutMapping("/car/{carId}")
	 public ResponseEntity<Void> updateCar(@PathVariable Long carId,@ModelAttribute CarDto carDto) throws IOException{
		try {
			boolean success=adminService.updateCar(carId,carDto);

			if(success)return ResponseEntity.status(HttpStatus.OK).build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	 }
@GetMapping("/car/bookings")
	 public ResponseEntity<List<BookACarDto>> getBookings(){

		return ResponseEntity.ok(adminService.getBookings());

	 }
@GetMapping("/car/booking/{bookingId}/{status}")
	 public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId,@PathVariable String status){

		boolean success= adminService.changeBookingStatus(bookingId,status);

		if(success)return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();

	}

	@PostMapping("/car/search")
	public  ResponseEntity<?> searching (@RequestBody  SearchCarDto searchCarDto){

		return ResponseEntity.ok(adminService.searchCar(searchCarDto));
	}



}
