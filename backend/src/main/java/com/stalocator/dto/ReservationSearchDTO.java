package com.stalocator.dto;

import java.time.LocalDate;

import com.stalocator.entities.TypeDorm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSearchDTO extends BaseDTO{

    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
    private Integer beds;
    private TypeDorm typeDorm;
    //private Long customerId;
    private String hostelname;
}