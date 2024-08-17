package com.stalocator.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.stalocator.custom_exec.ResourceNotFoundException;

public class Validations {
	
	
	
	public static void validateDates(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();

        // Check if the start date is in the future
        if (startDate.isBefore(now)) {
            throw new ResourceNotFoundException("Reservation start date must be in the future.");
        }

        // Check if the end date is after the start date
        if (!endDate.isAfter(startDate)) {
            throw new ResourceNotFoundException("Reservation end date must be after the start date.");
        }

        // Check if the duration is more than one month
        long durationInDays = ChronoUnit.DAYS.between(startDate, endDate);
        if (durationInDays > 31) {
            throw new ResourceNotFoundException("Duration exceeds one month");
        }
    }
	
}
