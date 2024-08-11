package com.stallocator.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    private LocalDateTime bookingStartDate;
    private LocalDateTime bookingEndDate;
    private Long customerId;
    private Long hostelId;
}
