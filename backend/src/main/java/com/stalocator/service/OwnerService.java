package com.stalocator.service;

import java.util.List;

import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.AuthDTO;
import com.stalocator.dto.OwnerDTO;
import com.stalocator.dto.OwnerRequestDTO;

public interface OwnerService {

	List<OwnerDTO> getAllOwners();
	
	OwnerDTO getOwner(String em);
	
	ApiResponse addOwner(OwnerDTO ownerDTO);
	
	ApiResponse updateOwner(String em, OwnerRequestDTO ownerRequestDTO);
	
	ApiResponse deleteOwner(String em);
	
	OwnerDTO authenticateUser(AuthDTO dto);
}
