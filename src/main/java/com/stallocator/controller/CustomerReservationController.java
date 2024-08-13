package com.stallocator.controller;

import static com.stallocator.utils.auth.authenticate_ResModule;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.ReservationRequestDTO;
import com.stallocator.service.ReservationServiceImpl;

@RestController@CrossOrigin(origins = "http://10.0.2.15:3000")
@RequestMapping("/customers/reservations")
public class CustomerReservationController {
	@Autowired
	ReservationServiceImpl ReservationServiceImpl;
	
	@GetMapping("/{custid}")
	public ResponseEntity<?> getAllReservations(@PathVariable Long custid, HttpServletRequest request){
		authenticate_ResModule(request, custid);
		return ResponseEntity.ok(ReservationServiceImpl.getAllReservationsByCustomer(custid));
		
	}
	
//	@GetMapping("/{em}")
//	public ResponseEntity<?> getReservation(@PathVariable String em){
//		authenticate_ResModule(request, id);
//		return ResponseEntity.ok(ReservationServiceImpl.getReservation(em));	
//		
//	}
	
	//WORK PENDING
	
	@PostMapping("/{custid}")
	public ResponseEntity<?> addReservation(@PathVariable Long custid, @RequestBody ReservationRequestDTO reservationRequestDTO, HttpServletRequest request){
		authenticate_ResModule(request, custid);
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(ReservationServiceImpl.addReservation(custid,reservationRequestDTO));
		}
		catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PutMapping("/{custid}/{resid}")
	public ResponseEntity<?> updateReservation(@PathVariable Long custid,@PathVariable Long resid, @RequestBody ReservationRequestDTO reservationRequestDTO, HttpServletRequest request){
		authenticate_ResModule(request, custid);	
		return ResponseEntity.status(HttpStatus.OK).body(ReservationServiceImpl.updateReservation(custid,resid,reservationRequestDTO));
	
	}

	@DeleteMapping("/{custid}/{resid}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long custid,@PathVariable Long resid, HttpServletRequest request){
		authenticate_ResModule(request, custid);		
		return ResponseEntity.status(HttpStatus.OK).body(ReservationServiceImpl.deleteReservation(custid,resid));
	
	}
}
