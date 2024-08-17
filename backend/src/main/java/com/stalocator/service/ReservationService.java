package com.stalocator.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.ReservationDTO;
import com.stalocator.dto.ReservationRequestDTO;
import com.stalocator.dto.ReservationSearchDTO;
import com.stalocator.entities.TypeDorm;

public interface ReservationService {
	List<ReservationDTO> getCurrentReservations();
	List<ReservationSearchDTO> getAllCurrentReservationsByCustomer(Long custID);
	List<ReservationDTO> getAllReservationsByHostel(Long hostelID);
	ReservationDTO getReservation(Long resid);
	ApiResponse addReservation(Long custid,ReservationRequestDTO reservationRequestDTO);
	ApiResponse updateReservation(Long custid,Long resid,ReservationRequestDTO reservationRequestDTO);
	ApiResponse deleteReservation(Long custid,Long resid);
	List<ReservationDTO> getPastReservations();
	List<ReservationDTO> getAllPastReservationsByCustomer(Long custid);
	Map<TypeDorm, Long> getHostelReservedBedsByDuration(Long hostelID, LocalDate bookingStartDate, LocalDate bookingEndDate);
}
