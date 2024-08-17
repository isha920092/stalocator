package com.stalocator.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.RoomDTO;
import com.stalocator.dto.RoomRequestDTO;
import com.stalocator.entities.Hostel;
import com.stalocator.entities.Room;
import com.stalocator.repository.RoomRepository;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<RoomDTO> getAllRoomsInHostel(Long hostelId) {
		return roomRepository.findByHostelId(hostelId).stream()
				.map(res->(modelMapper.map(res, RoomDTO.class))).collect(Collectors.toList());
	}

	@Override
	public RoomDTO getRoomDetails(Long roomId) {
		Room room = roomRepository.findById(roomId)
				.orElseThrow(() -> new ResourceNotFoundException("Room not found"));
		return modelMapper.map(room, RoomDTO.class);
	}

	@Override
	public ApiResponse addRoom(Long hostelId, RoomRequestDTO roomRequestDTO) {
		Room room = modelMapper.map(roomRequestDTO, Room.class);
		Hostel hostel = entityManager.getReference(Hostel.class, hostelId);
		room.setHostel(hostel);
		hostel.addRoom(room);
		roomRepository.save(room);
		return new ApiResponse("Room created");
	}

	@Override
	public ApiResponse updateRoom(Long hostelId, Long roomId, RoomRequestDTO roomRequestDTO) {
		Room originalRoom = roomRepository.findById(roomId).get();
		if(roomRepository.existsById(roomId) && originalRoom.getHostel().getId()==hostelId) {
			Room room = modelMapper.map(roomRequestDTO, Room.class);
			Hostel hostel = entityManager.getReference(Hostel.class, originalRoom.getHostel().getId());
			room.setHostel(hostel);
			room.setId(roomId);
			room = roomRepository.save(room);
			return new ApiResponse("Room details updated");
		}
		else throw new ResourceNotFoundException("Room not found");
	}

	@Override
	public ApiResponse deleteRoom(Long hostelId, Long roomId) {
		Room room = roomRepository.findById(roomId).get();
		if(roomRepository.existsById(roomId) && room.getHostel().getId()==hostelId) {
			roomRepository.deleteById(roomId);
			return new ApiResponse("Room deleted");
		}
		else throw new ResourceNotFoundException("Room not found");
	}

}
