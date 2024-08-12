package com.stallocator.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stallocator.entities.Customer;
import com.stallocator.entities.Hostel;
import com.stallocator.entities.TypeDorm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO extends BaseDTO {
    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate;
    private Integer beds;
    private TypeDorm typeDorm;
    @JsonIgnore
    private Customer customer;
    @JsonIgnore
    private Hostel hostel;
}
