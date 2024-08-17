package com.stalocator.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.dto.AuthDTO;
import com.stalocator.entities.Admin;
import com.stalocator.entities.Customer;
import com.stalocator.entities.Owner;
import com.stalocator.entities.Role;
import com.stalocator.repository.AdminRepository;
import com.stalocator.repository.CustomerRepository;
import com.stalocator.repository.OwnerRepository;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private OwnerRepository ownerRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    ModelMapper modelMapper;
    
    @Override
    public Role findRole(AuthDTO dto) {
        // Check if customer exists with the provided credentials
        Optional<Customer> customerOptional = customerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPwd());
        if (customerOptional.isPresent()) {
            return customerOptional.get().getRole();
        }
        
        // Check if admin exists with the provided credentials
        Optional<Admin> adminOptional = adminRepository.findByEmailAndPassword(dto.getEmail(), dto.getPwd());
        if (adminOptional.isPresent()) {
            return adminOptional.get().getRole();
        }
        
        // Check if owner exists with the provided credentials
        Optional<Owner> ownerOptional = ownerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPwd());
        if (ownerOptional.isPresent()) {
            return ownerOptional.get().getRole();
        }
        
        // If no valid credentials were found, throw an exception
        throw new ResourceNotFoundException("Invalid credentials");
    }
}
