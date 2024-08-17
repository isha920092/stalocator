package com.stalocator.controller;

import static com.stalocator.utils.auth.authenticate_OwnerModule;

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

import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.OwnerDTO;
import com.stalocator.dto.OwnerRequestDTO;
import com.stalocator.service.OwnerService;
import com.stalocator.service.ReservationService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://10.0.2.15:3000")
@RequestMapping("/owner")
public class OwnerController {
	
	@Autowired
	OwnerService ownerService;
	
	@Autowired
	ReservationService reservationService;
	
	@GetMapping
	public ResponseEntity<?> getAllCustomers(){
		return ResponseEntity.ok(ownerService.getAllOwners());	
		
	}
	
	@GetMapping("/{ownerid}/{hostelid}")
	public ResponseEntity<?> getAllReservationsByHostel(@PathVariable Long ownerid, @PathVariable Long hostelid,HttpServletRequest request){
		return ResponseEntity.ok(reservationService.getAllReservationsByHostel(hostelid));	
		
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<?> getOwner(@PathVariable String email, HttpServletRequest request){
		authenticate_OwnerModule(request, email);
		return ResponseEntity.ok(ownerService.getOwner(email));	
		
	}
	
	@PostMapping
	public ResponseEntity<?> addNewOwner(@RequestBody OwnerDTO ownerDTO) { 
		try {
			System.out.println("in add owner" + ownerDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(ownerService.addOwner(ownerDTO));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PutMapping("/{email}")
	public ResponseEntity<?> updateCustomer(@PathVariable String email, @RequestBody OwnerRequestDTO ownerRequestDTO, HttpServletRequest request) {
		authenticate_OwnerModule(request, email);
			return ResponseEntity.status(HttpStatus.OK).body(ownerService.updateOwner(email, ownerRequestDTO));	
	}

	@DeleteMapping("/{email}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String email, HttpServletRequest request){
		authenticate_OwnerModule(request, email);
			return ResponseEntity.status(HttpStatus.OK).body(ownerService.deleteOwner(email));
	}

}
