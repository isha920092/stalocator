package com.stalocator.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stalocator.entities.Customer;
import com.stalocator.entities.Hostel;
import com.stalocator.entities.TypeDorm;

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
