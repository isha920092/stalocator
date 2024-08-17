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
import com.stalocator.dto.RoomRequestDTO;
import com.stalocator.service.RoomService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://10.0.2.15:3000")
@RequestMapping("/owner/hostels/rooms")
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	@GetMapping("/{ownerId}/{hostelId}")
	public ResponseEntity<?> getAllRoomsInHostel(@PathVariable Long ownerId, @PathVariable Long hostelId, HttpServletRequest request) {
		authenticate_HostelAndRoomModule(request, ownerId);
		return ResponseEntity.ok(roomService.getAllRoomsInHostel(hostelId));		
	}
	
	@GetMapping("/{ownerId}/{hostelId}/{roomId}")
	public ResponseEntity<?> getCurrentHostelDetails(@PathVariable Long hostelId, @PathVariable Long ownerId, 
			@PathVariable Long roomId, HttpServletRequest request) {
		authenticate_HostelAndRoomModule(request, ownerId);
		return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoomDetails(roomId));
	}

	@PostMapping("/{ownerId}/{hostelId}")
	public ResponseEntity<?> addHostel(@PathVariable Long ownerId, @PathVariable Long hostelId,
			@RequestBody RoomRequestDTO roomRequestDTO, HttpServletRequest request){
		authenticate_HostelAndRoomModule(request, ownerId);
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addRoom(hostelId, roomRequestDTO));
		}
		catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PutMapping("/{ownerId}/{hostelId}/{roomId}")
	public ResponseEntity<?> updateHostel(@PathVariable Long ownerId, @PathVariable Long hostelId, 
			@PathVariable Long roomId, @RequestBody RoomRequestDTO roomRequestDTO, HttpServletRequest request){
		authenticate_HostelAndRoomModule(request, ownerId);	
		return ResponseEntity.status(HttpStatus.OK).body(roomService.updateRoom(hostelId, roomId, roomRequestDTO));
	}

	@DeleteMapping("/{ownerId}/{hostelId}/{roomId}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long ownerId, @PathVariable Long hostelId, 
			@PathVariable Long roomId, HttpServletRequest request){
		authenticate_HostelAndRoomModule(request, ownerId);		
		return ResponseEntity.status(HttpStatus.OK).body(roomService.deleteRoom(hostelId, roomId));	
	}
	
}
