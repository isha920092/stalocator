package com.stallocator.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stallocator.custom_exc.ResourceNotFoundException;
import com.stallocator.dto.AuthDTO;
import com.stallocator.entities.Admin;
import com.stallocator.entities.Customer;
import com.stallocator.entities.Role;
import com.stallocator.repository.AdminRepository;
import com.stallocator.repository.CustomerRepository;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public Role findRole(AuthDTO dto) {
	    Optional<Customer> customerOptional = customerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPwd());
	    if (customerOptional.isPresent()) {
	        return customerOptional.get().getRole();
	    } else {
	        Optional<Admin> adminOptional = adminRepository.findByEmailAndPassword(dto.getEmail(), dto.getPwd());
	        if (adminOptional.isPresent()) {
	            return adminOptional.get().getRole();
	        } else {
	            throw new ResourceNotFoundException("invalid credentials");
	        }
	    }
	}

}
