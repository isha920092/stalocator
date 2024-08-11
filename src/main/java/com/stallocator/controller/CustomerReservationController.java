package com.stallocator.controller;

import static com.stallocator.utils.auth.authenticate_byId;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.ReservationDTO;
import com.stallocator.dto.ReservationRequestDTO;
import com.stallocator.service.ReservationServiceImpl;

@RestController
@RequestMapping("/customers/reservations")
public class CustomerReservationController {
	@Autowired
	ReservationServiceImpl ReservationServiceImpl;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAllReservations(@PathVariable Long id, HttpServletRequest request){
		authenticate_byId(request, id);
		return ResponseEntity.ok(ReservationServiceImpl.getAllReservationsByCustomer(id));
		
	}
	
//	@GetMapping("/{em}")
//	public ResponseEntity<?> getReservation(@PathVariable String em){
//		authenticate_byId(request, id);
//		return ResponseEntity.ok(ReservationServiceImpl.getReservation(em));	
//		
//	}
	
	//WORK PENDING
	
	@PostMapping("/{id}")
	public ResponseEntity<?> addReservation(@PathVariable Long id, @RequestBody ReservationRequestDTO reservationRequestDTO, HttpServletRequest request){
		authenticate_byId(request, id);
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(ReservationServiceImpl.addReservation(reservationRequestDTO));
		}
		catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateReservation(@PathVariable Long id, @RequestBody ReservationRequestDTO reservationRequestDTO, HttpServletRequest request){
		authenticate_byId(request, id);	
		return ResponseEntity.status(HttpStatus.OK).body(ReservationServiceImpl.updateReservation(id,reservationRequestDTO));
	
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long id, HttpServletRequest request){
		authenticate_byId(request, id);		
		return ResponseEntity.status(HttpStatus.OK).body(ReservationServiceImpl.deleteReservation(id));
	
	}
}
