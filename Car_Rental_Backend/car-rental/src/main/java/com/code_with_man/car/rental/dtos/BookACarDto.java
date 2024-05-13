package com.code_with_man.car.rental.dtos;

import com.code_with_man.car.rental.enums.BookCarStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookACarDto {
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;
    private Long carId;
    private Long userId;
    private String userName;
    private String email;
}
