package com.stalocator.service;

import java.util.List;

import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.HostelAddDTO;
import com.stalocator.dto.HostelDTO;
import com.stalocator.dto.HostelRequestDTO;
import com.stalocator.dto.HostelSearchDTO;
import com.stalocator.dto.SearchDTO;

public interface HostelService {

	HostelDTO getHostel(Long hostelId);

	ApiResponse addHostel(Long ownerId, HostelAddDTO hostelRequestDTO);

	ApiResponse updateHostel(Long ownerId, Long hostelId, HostelRequestDTO hostelRequestDTO);

	ApiResponse deleteReservation(Long ownerId, Long hostelId);
	
	List<HostelDTO> getAllHostelsByOwner(Long ownerId);

	List<HostelSearchDTO> getHostelAvailability(SearchDTO search);

}
