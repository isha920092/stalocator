package com.stalocator.controller;

import static com.stalocator.utils.auth.authenticate_AdminModule;

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

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.service.AdminServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminServiceImpl adminServiceImpl;
	
	@GetMapping
	public ResponseEntity<?> getPendingApprovals(HttpServletRequest request){
		//authenticate_AdminModule(request);
		return ResponseEntity.ok(adminServiceImpl.getPendingApprovals());	
	}
	
	@GetMapping("/{ownerId}")
	public ResponseEntity<?> approveOwner(@PathVariable Long ownerId, HttpServletRequest request) {
	    //authenticate_AdminModule(request);
	    
	    try {
	        adminServiceImpl.approveOwner(ownerId); // Call the service method to approve the owner
	        return ResponseEntity.ok("Owner approved successfully");
	    } catch (ResourceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while approving the owner");
	    }
	}

	
	@GetMapping("/customers")
	public ResponseEntity<?> getAllCustomers(HttpServletRequest request){
		//authenticate_AdminModule(request);
		return ResponseEntity.ok(adminServiceImpl.getAllCustomers());	
	}
	
	@DeleteMapping("/customers/{custId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long custId, HttpServletRequest request){
		//authenticate_AdminModule(request);
		return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.deleteCustomer(custId));
	}
	
	@GetMapping("/hostels")
	public ResponseEntity<?> getAllHostels(HttpServletRequest request){
		authenticate_AdminModule(request);
		return ResponseEntity.ok(adminServiceImpl.getAllHostels());	
	}
	
	@DeleteMapping("/hostels/{hostelId}")
	public ResponseEntity<?> deleteHostel(@PathVariable Long hostelId, HttpServletRequest request){
		authenticate_AdminModule(request);
		return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.deleteHostel(hostelId));
	}
	
	@GetMapping("/owners")
	public ResponseEntity<?> getAllOwners(HttpServletRequest request){
		//authenticate_AdminModule(request);
		return ResponseEntity.ok(adminServiceImpl.getAllOwners());	
	}
	
	@DeleteMapping("/owners/{ownerId}")
	public ResponseEntity<?> deleteOwner(@PathVariable Long ownerId, HttpServletRequest request){
		//authenticate_AdminModule(request);
		return ResponseEntity.status(HttpStatus.OK).body(adminServiceImpl.deleteOwner(ownerId));
	}
}
