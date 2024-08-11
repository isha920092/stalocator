package com.stallocator.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stallocator.entities.Customer;
import com.stallocator.custom_exc.ResourceNotFoundException;
import com.stallocator.dto.AuthDTO;
import com.stallocator.entities.Role;
import com.stallocator.repository.CustomerRepository;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public Role findRole(AuthDTO dto) {
		if(customerRepository.existsByEmail(dto.getEmail())) {
		Customer cust = customerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPwd())
	            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
				return cust.getRole();
		}
		else throw new ResourceNotFoundException("invalid credentials");
	
	}

}
