package com.stallocator.controller;

import static com.stallocator.utils.auth.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stallocator.service.AdminServiceImpl;
import com.stallocator.service.CustomerServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://10.0.2.15:3000")
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminServiceImpl adminServiceImpl;
	
//	@GetMapping
//	public ResponseEntity<?> getPendingApprovals(HttpServletRequest request){
//		return ResponseEntity.ok(adminServiceImpl.getPendingApprovals());	
//		
//	}
	
//	@GetMapping("/{custId}")
//	public ResponseEntity<?> approveOwner(@PathVariable Long ownerId, HttpServletRequest request){
//		authenticate_AdminModule(request);
//		return ResponseEntity.ok(adminServiceImpl.approveOwner(ownerId));	
//		
//	}
	
	@GetMapping("/customers")
	public ResponseEntity<?> getAllCustomers(HttpServletRequest request){
		authenticate_AdminModule(request);
		return ResponseEntity.ok(adminServiceImpl.getAllCustomers());	
		
	}
	
	@DeleteMapping("/customers/{custId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long custId, HttpServletRequest request){
		authenticate_AdminModule(request);
			return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.deleteCustomer(custId));
	
	}
	
//	@GetMapping("/hostels")
//	public ResponseEntity<?> getAllHostels(){
//	authenticate_AdminModule(request);
//		return ResponseEntity.ok(adminServiceImpl.getHostels());	
//		
//	}
//	
//	@DeleteMapping("/hostels/{hostelId}")
//	public ResponseEntity<?> deleteHostel(@PathVariable Long hostelId, HttpServletRequest request){
//		authenticate_AdminModule(request);
//			return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.deleteHostel(hostelId));
//	
//	}
//	
//	@GetMapping("/owners")
//	public ResponseEntity<?> getAllOwners(){
//	authenticate_AdminModule(request);

//		return ResponseEntity.ok(adminServiceImpl.getOwners());	
//		
//	}
//	
//	@DeleteMapping("/owners/{ownerId}")
//	public ResponseEntity<?> deleteHostel(@PathVariable Long ownerId, HttpServletRequest request){
//		authenticate_AdminModule(request);
//			return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.deleteHostel(ownerId));
//	
//	}

}
