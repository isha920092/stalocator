package com.stallocator.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stallocator.custom_exc.ResourceNotFoundException;
import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.AuthDTO;
import com.stallocator.dto.CustomerDTO;
import com.stallocator.entities.Customer;
import com.stallocator.repository.CustomerRepository;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{


	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<CustomerDTO> getAllCustomers(){
		return customerRepository.findAll().stream().map(cust->(modelMapper.map(cust, CustomerDTO.class))).collect(Collectors.toList());
	}

	public CustomerDTO getCustomer(String em) {
//		String loggedInUserEmail
//		
//		if (!loggedInUserEmail.equals(em)) {
//            throw new SecurityException("You are not authorized to update this customer.");
//        }
	    Customer cust = customerRepository.findByEmail(em)
	            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	    return modelMapper.map(cust, CustomerDTO.class);
	}

	@Override
	public ApiResponse addCustomer(CustomerDTO customerDTO) {
		Customer cust=modelMapper.map(customerDTO, Customer.class);
		customerRepository.save(cust);
		return new ApiResponse("customer created");
	}

	@Override
	public ApiResponse updateCustomer(String em, CustomerDTO customerDTO) {
		if(customerRepository.existsByEmail(em)) {
			Customer cust=modelMapper.map(customerDTO, Customer.class);
			cust.setEmail(em);
			cust=customerRepository.save(cust);
		return new ApiResponse("customer updated");
		}
		else throw new ResourceNotFoundException("Customer not found");
	}

	@Override
	public ApiResponse deleteCustomer(String em) {
		if(customerRepository.existsByEmail(em)) {
			customerRepository.deleteByEmail(em);
		return new ApiResponse("customer deleted");
		}
		else throw new ResourceNotFoundException("Customer not found");
	}

	@Override
	public CustomerDTO authenticateUser(AuthDTO dto) {
		Customer cust = customerRepository.
				findByEmailAndPassword(dto.getEmail(), dto.getPwd())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Authentication"));
				return modelMapper.map(cust, CustomerDTO.class);
	}

//	 @Override
//	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//	        return customerRepository.findByEmail(email)
//	            .map(CustomUserDetails::new)
//	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//	    }

}
