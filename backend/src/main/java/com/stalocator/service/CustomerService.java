package com.stalocator.service;

import java.util.List;

import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.AuthDTO;
import com.stalocator.dto.CustomerDTO;
import com.stalocator.dto.CustomerRequestDTO;

public interface CustomerService {
	List<CustomerDTO> getAllCustomers();
	CustomerDTO getCustomer(String em);
	ApiResponse addCustomer(CustomerDTO customerDTO);
	ApiResponse updateCustomer(String em,CustomerRequestDTO customerRequestDTO);
	ApiResponse deleteCustomer(String em);
	CustomerDTO authenticateUser(AuthDTO dto);
	ApiResponse setToken(Long id, String tok);
	String getToken(Long id);
}
