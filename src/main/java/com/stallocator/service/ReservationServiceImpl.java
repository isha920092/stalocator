package com.stallocator.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stallocator.custom_exc.ResourceNotFoundException;
import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.ReservationDTO;
import com.stallocator.dto.ReservationRequestDTO;
import com.stallocator.entities.Reservation;
import com.stallocator.repository.ReservationRepository;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<ReservationDTO> getAllReservations() {
		return reservationRepository.findAll().stream().map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}

	@Override
	public ReservationDTO getReservation(Long id) {
		Reservation res = reservationRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
	    return modelMapper.map(res, ReservationDTO.class);
	}

	//WORK PENDING
	@Override
	public ApiResponse addReservation(ReservationRequestDTO reservationRequestDTO) {
		Reservation res =modelMapper.map(reservationRequestDTO, Reservation.class);
		reservationRepository.save(res);
		return new ApiResponse("reservation created");
	}

	@Override
	public ApiResponse updateReservation(Long id,ReservationRequestDTO reservationRequestDTO) {
		if(reservationRepository.existsById(id)) {
			Reservation res =modelMapper.map(reservationRequestDTO, Reservation.class);
			res.setId(id);
			res=reservationRepository.save(res);
		return new ApiResponse("Reservation updated");
		}
		else throw new ResourceNotFoundException("Reservation not found");
	}

	@Override
	public ApiResponse deleteReservation(Long id) {
		if(reservationRepository.existsById(id)) {
			reservationRepository.deleteById(id);
		return new ApiResponse("Reservation deleted");
		}
		else throw new ResourceNotFoundException("Reservation not found");
	}

	@Override
	public List<ReservationDTO> getAllReservationsByCustomer(Long id) {
		return reservationRepository.findByCustomerId(id).stream().map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}

	@Override
	public List<ReservationDTO> getAllReservationsByHostel(Long hostelID) {
		return reservationRepository.findByHostel(hostelID).stream().map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}

}
