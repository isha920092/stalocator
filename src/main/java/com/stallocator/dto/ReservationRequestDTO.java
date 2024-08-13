package com.stallocator.dto;

import java.time.LocalDate;

import com.stallocator.entities.TypeDorm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
    private Integer beds;
    private TypeDorm typeDorm;
    //private Long customerId;
    private Long hostelId;
}
