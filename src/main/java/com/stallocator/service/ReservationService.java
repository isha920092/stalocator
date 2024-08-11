package com.stallocator.service;

import java.util.List;

import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.ReservationDTO;
import com.stallocator.dto.ReservationRequestDTO;

public interface ReservationService {
	List<ReservationDTO> getAllReservations();
	List<ReservationDTO> getAllReservationsByCustomer(Long custID);
	List<ReservationDTO> getAllReservationsByHostel(Long hostelID);
	ReservationDTO getReservation(Long id);
	ApiResponse addReservation(ReservationRequestDTO reservationRequestDTO);
	ApiResponse updateReservation(Long id,ReservationRequestDTO reservationRequestDTO);
	ApiResponse deleteReservation(Long id);
}
