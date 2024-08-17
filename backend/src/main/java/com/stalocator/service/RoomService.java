package com.stalocator.service;

import java.util.List;

import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.RoomDTO;
import com.stalocator.dto.RoomRequestDTO;

public interface RoomService {

	List<RoomDTO> getAllRoomsInHostel(Long hostelId);

	RoomDTO getRoomDetails(Long roomId);

	ApiResponse addRoom(Long hostelId, RoomRequestDTO roomRequestDTO);

	ApiResponse updateRoom(Long hostelId, Long roomId, RoomRequestDTO roomRequestDTO);

	ApiResponse deleteRoom(Long hostelId, Long roomId);

}
