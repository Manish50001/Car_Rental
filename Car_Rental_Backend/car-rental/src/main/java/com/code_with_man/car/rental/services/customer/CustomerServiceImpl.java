package com.code_with_man.car.rental.services.customer;

import com.code_with_man.car.rental.dtos.BookACarDto;
import com.code_with_man.car.rental.dtos.CarDto;
import com.code_with_man.car.rental.dtos.CarDtoListDto;
import com.code_with_man.car.rental.dtos.SearchCarDto;
import com.code_with_man.car.rental.entities.BookACar;
import com.code_with_man.car.rental.entities.Car;
import com.code_with_man.car.rental.entities.User;
import com.code_with_man.car.rental.enums.BookCarStatus;
import com.code_with_man.car.rental.repositories.BookACarRepository;
import com.code_with_man.car.rental.repositories.CarRepository;
import com.code_with_man.car.rental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service

public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars () {


        return carRepository.findAll().stream().map(Car::getCarDto).toList();
    }

    @Override
    public boolean bookACar (BookACarDto bookACarDto) {
        Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
        Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());

        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            Car existingCar = optionalCar.get();
            BookACar bookACar = new BookACar();
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);

            long diffInMilliSeconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();

            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);

            bookACar.setDays(days);
            bookACar.setPrice(existingCar.getPrice() * days);
            bookACar.setFromDate(bookACarDto.getFromDate());
            bookACar.setToDate(bookACarDto.getToDate());
            bookACarRepository.save(bookACar);

            return true;
        }

        return false;
    }

    @Override
    public CarDto getCarById (Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public List<BookACarDto> getBookingsByUserId (Long userId) {

       return bookACarRepository.findAllByUserId(userId).stream().map(BookACar::getBookACarDto).collect(Collectors.toList());

    }

    @Override
    public CarDtoListDto searchCar (SearchCarDto searchCarDto) {
        Car car=new Car();
        car.setImage(car.getImage());
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
