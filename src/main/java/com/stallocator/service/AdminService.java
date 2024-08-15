package com.stallocator.service;

import com.stallocator.dto.AdminDTO;
import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.AuthDTO;

public interface AdminService {

	AdminDTO authenticateUser(AuthDTO dto);

	ApiResponse deleteCustomer(Long custId);

	void approveOwner(Long ownerId);

}
