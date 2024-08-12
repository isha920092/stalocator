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
import com.stallocator.dto.CustomerRequestDTO;
import com.stallocator.entities.Customer;
import com.stallocator.entities.Role;
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
		cust.setRole(Role.CUSTOMER);
		customerRepository.save(cust);
		return new ApiResponse("customer created");
	}

	@Override
	public ApiResponse updateCustomer(String em, CustomerRequestDTO customerRequestDTO) {
	    // Retrieve the existing customer by email
	    Customer existingCustomer = customerRepository.findByEmail(em)
	            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

	    // Map the DTO to the existing customer entity
	    modelMapper.map(customerRequestDTO, existingCustomer);

	    // Ensure that the role and email are preserved correctly
	    existingCustomer.setRole(existingCustomer.getRole()); // Preserve the role
	    existingCustomer.setEmail(em); // Ensure the email remains unchanged

	    // Save the updated customer entity
	    customerRepository.save(existingCustomer);
	    return new ApiResponse("Customer updated");
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
