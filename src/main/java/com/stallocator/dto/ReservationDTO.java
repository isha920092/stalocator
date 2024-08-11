package com.stallocator.dto;

import java.sql.Date;

import com.stallocator.entities.Customer;
import com.stallocator.entities.Hostel;
import com.stallocator.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO extends BaseDTO {
    private Date bookingStartDate;
    private Date bookingEndDate;
    private Customer customer;
    private Hostel hostel;
}
