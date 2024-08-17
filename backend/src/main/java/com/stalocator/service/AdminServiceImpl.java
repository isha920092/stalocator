package com.stalocator.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.dto.AdminDTO;
import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.AuthDTO;
import com.stalocator.dto.CustomerDTO;
import com.stalocator.dto.HostelDTO;
import com.stalocator.dto.OwnerDTO;
import com.stalocator.entities.Admin;
import com.stalocator.entities.Owner;
import com.stalocator.repository.AdminRepository;
import com.stalocator.repository.CustomerRepository;
import com.stalocator.repository.HostelRepository;
import com.stalocator.repository.OwnerRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private HostelRepository hostelRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public AdminDTO authenticateUser(AuthDTO dto) {
		Admin admin = adminRepository.
				findByEmailAndPassword(dto.getEmail(), dto.getPwd())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Authentication"));
				return modelMapper.map(admin, AdminDTO.class);
	}
	
	@Override
	public List<OwnerDTO> getPendingApprovals() {
	    // Fetch the list of owners who are not approved
	    List<Owner> pendingOwners = ownerRepository.findByApproved(false);
	    
	    // Map each Owner entity to OwnerDTO
	    List<OwnerDTO> pendingOwnerDTOs = pendingOwners.stream()
	            .map(owner -> modelMapper.map(owner, OwnerDTO.class))
	            .collect(Collectors.toList());
	    
	    return pendingOwnerDTOs;
	}

	
	 @Override
	    public void approveOwner(Long ownerId) {
	        Owner owner = ownerRepository.findById(ownerId)
	                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
	        
	        // Set the approval status
	        owner.setApproved(true);
	        
	        // Save the updated entity
	        ownerRepository.save(owner);
	    }

	public List<CustomerDTO> getAllCustomers(){
		return customerRepository.findAll().stream().map(cust->(modelMapper.map(cust, CustomerDTO.class))).collect(Collectors.toList());
	}
	
	@Override
	public ApiResponse deleteCustomer(Long custId) {
		if(customerRepository.existsById(custId)) {
			customerRepository.deleteById(custId);;
		return new ApiResponse("customer deleted");
		}
		else throw new ResourceNotFoundException("Customer not found");
	}
	
	@Override
	public List<HostelDTO> getAllHostels(){
		return hostelRepository.findAll().stream().map(hostel->(modelMapper.map(hostel, HostelDTO.class))).collect(Collectors.toList());
	}
	
	@Override
	public ApiResponse deleteHostel(Long custId) {
		if(hostelRepository.existsById(custId)) {
			hostelRepository.deleteById(custId);
			return new ApiResponse("hostel deleted");
		}
		else throw new ResourceNotFoundException("hostel not found");
	}
	
	@Override
	public List<OwnerDTO> getAllOwners(){
		return ownerRepository.findAll().stream().map(owner->(modelMapper.map(owner, OwnerDTO.class))).collect(Collectors.toList());
	}
	
	@Override
	public ApiResponse deleteOwner(Long ownerId) {
		if(ownerRepository.existsById(ownerId)) {
			ownerRepository.deleteById(ownerId);;
		return new ApiResponse("owner deleted");
		}
		else throw new ResourceNotFoundException("Owner not found");
	}
	
	
}
