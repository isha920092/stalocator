package com.stallocator.controller;

import static com.stallocator.utils.auth.authenticate_CustModule;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.CustomerDTO;
import com.stallocator.dto.CustomerRequestDTO;
import com.stallocator.service.CustomerServiceImpl;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@GetMapping
	public ResponseEntity<?> getAllCustomers(){
		return ResponseEntity.ok(customerServiceImpl.getAllCustomers());	
		
	}
	
	@GetMapping("/{em}")
	public ResponseEntity<?> getCustomer(@PathVariable String em, HttpServletRequest request){
		authenticate_CustModule(request, em);
		return ResponseEntity.ok(customerServiceImpl.getCustomer(em));	
		
	}
	
	@PostMapping
	public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customerDTO){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(customerServiceImpl.addCustomer(customerDTO));
		}
		catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Account with this email already exists. Try logging in"));
		}
	}
	
	@PutMapping("/{em}")
	public ResponseEntity<?> updateCustomer(@PathVariable String em,@RequestBody CustomerRequestDTO customerRequestDTO, HttpServletRequest request){
		authenticate_CustModule(request, em);
			return ResponseEntity.status(HttpStatus.OK).body(customerServiceImpl.updateCustomer(em,customerRequestDTO));
	
	}

	@DeleteMapping("/{em}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String em, HttpServletRequest request){
		authenticate_CustModule(request, em);
			return ResponseEntity.status(HttpStatus.OK).body(customerServiceImpl.deleteCustomer(em));
	
	}
}
