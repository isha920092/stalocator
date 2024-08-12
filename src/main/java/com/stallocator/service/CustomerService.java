package com.stallocator.service;

import java.util.List;

import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.AuthDTO;
import com.stallocator.dto.CustomerDTO;
import com.stallocator.dto.CustomerRequestDTO;

public interface CustomerService {
	List<CustomerDTO> getAllCustomers();
	CustomerDTO getCustomer(String em);
	ApiResponse addCustomer(CustomerDTO customerDTO);
	ApiResponse updateCustomer(String em,CustomerRequestDTO customerRequestDTO);
	ApiResponse deleteCustomer(String em);
	CustomerDTO authenticateUser(AuthDTO dto);
}
