package com.stallocator.service;


import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stallocator.custom_exc.ResourceNotFoundException;
import com.stallocator.dto.AdminDTO;
import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.AuthDTO;
import com.stallocator.dto.CustomerDTO;
import com.stallocator.entities.Admin;
import com.stallocator.repository.AdminRepository;
import com.stallocator.repository.CustomerRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;
	
//	@Autowired
//	private OwnerRepository ownerRepository;
	
//	@Autowired
//	private HostelRepository hostelRepository;
	
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
	
//	@Override
//	public List<OwnerDTO> getPendingApprovals() {
//	    return ownerRepository.findByApproved(false);
//	}
	
	@Override
	public void approveOwner(Long ownerId) {
//		ownerRepository.getById(ownerId).setApproved(true);
			
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
	
//	@Override
//	public List<HostelDTO> getAllHostels(){
//		return hostelRepository.findAll().stream().map(hostel->(modelMapper.map(hostel, HostelDTO.class))).collect(Collectors.toList());
//	}
	
//	@Override
//	public ApiResponse deleteHostel(Long hostelId) {
//		if(hostelRepository.existsById(custId)) {
//			hostelRepository.deleteById(custId);;
//		return new ApiResponse("hostel deleted");
//		}
//		else throw new ResourceNotFoundException("hostel not found");
//	}
	
//	@Override
//	public List<CustomerDTO> getAllOwners(){
//		return ownerRepository.findAll().stream().map(owner->(modelMapper.map(owner, OwnerDTO.class))).collect(Collectors.toList());
//	}
	
//	@Override
//	public ApiResponse deleteOwner(Long ownerId) {
//		if(ownerRepository.existsById(ownerId)) {
//			ownerRepository.deleteById(ownerId);;
//		return new ApiResponse("owner deleted");
//		}
//		else throw new ResourceNotFoundException("Owner not found");
//	}
}
