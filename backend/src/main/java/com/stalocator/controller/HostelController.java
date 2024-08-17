package com.stalocator.controller;

import static com.stalocator.utils.auth.authenticate_HostelAndRoomModule;

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
import com.stalocator.dto.HostelAddDTO;
import com.stalocator.dto.HostelRequestDTO;
import com.stalocator.service.HostelService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://10.0.2.15:3000")
@RequestMapping("/owner/hostels")
public class HostelController {
	
	@Autowired
	HostelService hostelService;
	
	@GetMapping("/{ownerId}")
	public ResponseEntity<?> getAllHostelsByOwner(@PathVariable Long ownerId, HttpServletRequest request){
		//authenticate_HostelAndRoomModule(request, ownerId);
		return ResponseEntity.ok(hostelService.getAllHostelsByOwner(ownerId));		
	}
	
	@GetMapping("/{ownerId}/{hostelId}")
	public ResponseEntity<?> getCurrentHostelDetails(@PathVariable Long hostelId, @PathVariable Long ownerId, HttpServletRequest request) {
		authenticate_HostelAndRoomModule(request, ownerId);
		return ResponseEntity.status(HttpStatus.OK).body(hostelService.getHostel(hostelId));
	}

	@PostMapping("/{ownerId}")
	public ResponseEntity<?> addHostel(@PathVariable Long ownerId, @RequestBody HostelAddDTO hostelRequestDTO, HttpServletRequest request){
		//authenticate_HostelAndRoomModule(request, ownerId);
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(hostelService.addHostel(ownerId, hostelRequestDTO));
		}
		catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PutMapping("/{ownerId}/{hostelId}")
	public ResponseEntity<?> updateHostel(@PathVariable Long ownerId, @PathVariable Long hostelId, @RequestBody HostelRequestDTO hostelRequestDTO, HttpServletRequest request){
		authenticate_HostelAndRoomModule(request, ownerId);	
		return ResponseEntity.status(HttpStatus.OK).body(hostelService.updateHostel(ownerId, hostelId, hostelRequestDTO));
	}

	@DeleteMapping("/{ownerId}/{hostelId}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long ownerId, @PathVariable Long hostelId, HttpServletRequest request){
		authenticate_HostelAndRoomModule(request, ownerId);		
		return ResponseEntity.status(HttpStatus.OK).body(hostelService.deleteReservation(ownerId, hostelId));	
	}

}
