package com.stallocator.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.AuthDTO;
import com.stallocator.dto.CustomerDTO;
import com.stallocator.entities.Role;
import com.stallocator.service.CustomerServiceImpl;
import com.stallocator.service.LoginServiceImpl;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@PostMapping
	public ResponseEntity<?> userSignIn(@RequestBody AuthDTO dto) {
		try {		
			Role role=loginServiceImpl.findRole(dto);
			if(role.equals(Role.CUSTOMER)) {
				CustomerDTO authenticatedUser = customerServiceImpl.authenticateUser(dto);
				session.setAttribute("user", authenticatedUser);
				session.setAttribute("role", role);
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
