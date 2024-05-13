package com.code_with_man.car.rental.services.admin;

import com.code_with_man.car.rental.dtos.BookACarDto;
import com.code_with_man.car.rental.dtos.CarDto;
import com.code_with_man.car.rental.dtos.CarDtoListDto;
import com.code_with_man.car.rental.dtos.SearchCarDto;
import com.code_with_man.car.rental.entities.BookACar;
import com.code_with_man.car.rental.entities.Car;
import com.code_with_man.car.rental.enums.BookCarStatus;
import com.code_with_man.car.rental.repositories.BookACarRepository;
import com.code_with_man.car.rental.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	@Autowired
	private   CarRepository carRepository;
@Autowired
private BookACarRepository bookACarRepository;
	@Override
	public boolean postCar(CarDto carDto) throws IOException {
	    try {
	        Car car = new Car();
	        car.setName(carDto.getName());
	        car.setBrand(carDto.getBrand());
	        car.setColor(carDto.getColor());
	        car.setType(carDto.getType());
	        car.setDescription(carDto.getDescription());
	        car.setPrice(carDto.getPrice());
	        car.setTransmission(carDto.getTransmission());
	       car.setYear(carDto.getYear());
	      //   Handle potential data type mismatch (e.g., convert MultipartFile to byte[])
	        car.setImage(carDto.getImage().getBytes()); // Assuming MultipartFile has getBytes()

	        carRepository.save(car);
	        return true;
	    } catch (Exception e) {
	        System.err.println("Error saving car: " + e.getMessage());
	        e.printStackTrace(); // Log stack trace for debugging
	        return false;
	    }
	}

	@Override
	public List<CarDto> getAllCars () {


		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public void deleteCar (Long id) {
		carRepository.deleteById(id);
	}

	@Override
	public CarDto getCarById (Long id) {
		Optional<Car> optionalCar=carRepository.findById(id);

		return optionalCar.map(Car::getCarDto).orElse(null);
	}

	@Override
	public boolean updateCar (Long carId, CarDto carDto) throws IOException {
		Optional<Car> optionalCar = carRepository.findById(carId);
		if (optionalCar.isPresent()) {
			Car existingCar = optionalCar.get();
			if (carDto.getImage() != null)
				existingCar.setImage(carDto.getImage().getBytes());
				existingCar.setType(carDto.getType());
				existingCar.setYear(carDto.getYear());
				existingCar.setPrice(carDto.getPrice());
				existingCar.setBrand(carDto.getBrand());
				existingCar.setName(carDto.getName());
				existingCar.setDescription(carDto.getDescription());
				existingCar.setTransmission(carDto.getTransmission());
				existingCar.setColor(carDto.getColor());
				carRepository.save(existingCar);


			return true;
		}
			else {
				return false;
			}
		}

	@Override
	public List<BookACarDto> getBookings () {

		return bookACarRepository.findAll().stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
	}

	@Override
	public boolean changeBookingStatus (Long bookingId, String status) {
		Optional<BookACar>optionalBookACar=bookACarRepository.findById(bookingId);

if(optionalBookACar.isPresent()){
	BookACar existingBookACar=optionalBookACar.get();
	if(Objects.equals(status,"Approve"))
		existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
	else
		existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);

	bookACarRepository.save(existingBookACar);
	return true;
}
		return false;
	}

	@Override
	public CarDtoListDto searchCar (SearchCarDto searchCarDto) {

		Car car=new Car();

		car.setBrand(searchCarDto.getBrand());
		car.setColor(searchCarDto.getColor());
		car.setTransmission(searchCarDto.getTransmission());
		car.setType(searchCarDto.getType());
		ExampleMatcher exampleMatcher=ExampleMatcher.matchingAll()
				.withMatcher("brand",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("type",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("color",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("transmission",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<Car> carExample =Example.of(car,exampleMatcher);
		List<Car> carList=carRepository.findAll(carExample);
		CarDtoListDto carDtoListDto=new CarDtoListDto();
		carDtoListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));

		return carDtoListDto;

	}

}
