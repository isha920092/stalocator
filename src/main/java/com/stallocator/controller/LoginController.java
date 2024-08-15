package com.stallocator.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stallocator.dto.AdminDTO;
import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.AuthDTO;
import com.stallocator.dto.CustomerDTO;
import com.stallocator.entities.Role;
import com.stallocator.service.AdminServiceImpl;
import com.stallocator.service.CustomerServiceImpl;
import com.stallocator.service.LoginServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://10.0.2.15:3000")
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	AdminServiceImpl adminServiceImpl;
	
	@PostMapping
	public ResponseEntity<?> userSignIn(@RequestBody AuthDTO dto) {
		System.out.println(dto.getEmail());
		try {		
			Role role=loginServiceImpl.findRole(dto);
			if(role.equals(Role.ADMIN)) {
				AdminDTO authenticatedUser = adminServiceImpl.authenticateUser(dto);
				session.setAttribute("user", authenticatedUser);
				session.setAttribute("role", role);
				System.out.println(authenticatedUser);
				return ResponseEntity.ok(authenticatedUser);
			}
			
			else if(role.equals(Role.CUSTOMER)) {
				CustomerDTO authenticatedUser = customerServiceImpl.authenticateUser(dto);
				session.setAttribute("user", authenticatedUser);
				session.setAttribute("role", role);
				System.out.println(authenticatedUser);
				return ResponseEntity.ok(authenticatedUser);
			}
		} catch (RuntimeException e) {
			//invalid login 
			System.out.println(e);
			return ResponseEntity.
					status(HttpStatus.NOT_FOUND).
					body(new ApiResponse(e.getMessage()));
		}
		return null;

	}
}
