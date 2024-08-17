package com.stalocator.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.AuthDTO;
import com.stalocator.dto.CustomerDTO;
import com.stalocator.dto.CustomerRequestDTO;
import com.stalocator.entities.Customer;
import com.stalocator.entities.Role;
import com.stalocator.repository.AdminRepository;
import com.stalocator.repository.CustomerRepository;
import com.stalocator.repository.OwnerRepository;
import com.stalocator.utils.Validations;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{


	 @Autowired
	    private CustomerRepository customerRepository;
	    
	    @Autowired
	    private AdminRepository adminRepository;
	    
	    @Autowired
	    private OwnerRepository ownerRepository;
	
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
	            .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));
	    return modelMapper.map(cust, CustomerDTO.class);
	}

	@Override
	public ApiResponse addCustomer(CustomerDTO customerDTO) {
	    try {
	        if (isEmailInUse(customerDTO.getEmail())) {
	            return new ApiResponse("Account with this email already exists. Try logging in");
	        }
	        
	        Customer cust = modelMapper.map(customerDTO, Customer.class);
	        cust.setRole(Role.CUSTOMER);
	        customerRepository.save(cust);
	        System.out.println("Customer created successfully");
	        return new ApiResponse("Customer created");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ApiResponse("An error occurred: " + e.getMessage());
	    }
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
	
	public boolean isEmailInUse(String email) {
	    return customerRepository.existsByEmail(email)||
	           adminRepository.existsByEmail(email)||
	           ownerRepository.existsByEmail(email);
	}
	
	@Override
	public ApiResponse setToken(Long id,String tok) {
		Customer cust = customerRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

//	    // Map the DTO to the existing customer entity
//	    modelMapper.map(customerRequestDTO, existingCustomer);

	    // Ensure that the role and email are preserved correctly
	    cust.setToken(tok);

	    // Save the updated customer entity
	    customerRepository.save(cust);
	    return new ApiResponse("Customer updated");
	}
	
	@Override
	public String getToken(Long id) {
		return customerRepository.findById(id).get().getToken();
	}
	

//	 @Override
//	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//	        return customerRepository.findByEmail(email)
//	            .map(CustomUserDetails::new)
//	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//	    }

}
