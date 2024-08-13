package com.stallocator.service;

import java.util.List;

import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.ReservationDTO;
import com.stallocator.dto.ReservationRequestDTO;

public interface ReservationService {
	List<ReservationDTO> getAllReservations();
	List<ReservationDTO> getAllReservationsByCustomer(Long custID);
	List<ReservationDTO> getAllReservationsByHostel(Long hostelID);
	ReservationDTO getReservation(Long resid);
	ApiResponse addReservation(Long custid,ReservationRequestDTO reservationRequestDTO);
	ApiResponse updateReservation(Long custid,Long resid,ReservationRequestDTO reservationRequestDTO);
	ApiResponse deleteReservation(Long custid,Long resid);
}
