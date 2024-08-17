package com.stalocator.service;

import java.util.List;

import com.stalocator.dto.AdminDTO;
import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.AuthDTO;
import com.stalocator.dto.HostelDTO;
import com.stalocator.dto.OwnerDTO;

public interface AdminService {

	AdminDTO authenticateUser(AuthDTO dto);

	ApiResponse deleteCustomer(Long custId);

	void approveOwner(Long ownerId);

	List<OwnerDTO> getAllOwners();

	ApiResponse deleteOwner(Long ownerId);

	List<OwnerDTO> getPendingApprovals();

	List<HostelDTO> getAllHostels();

	ApiResponse deleteHostel(Long custId);

}
