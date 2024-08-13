package com.stallocator.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.stallocator.custom_exc.ResourceNotFoundException;
import com.stallocator.dto.CustomerDTO;
import com.stallocator.entities.Role;

public class auth {
	public static void authenticate_CustModule(HttpServletRequest request, String em) {
        HttpSession session = request.getSession(false); // Get the current session, but don't create a new one if it doesn't exist
        if (session != null && session.getAttribute("user") != null) {
            CustomerDTO currentUser = (CustomerDTO) session.getAttribute("user");
            // Check if the current user is authorized to modify the target user
            if(currentUser.getEmail().equals(em) || session.getAttribute("role") == Role.ADMIN)
            	return;
            else 
                // No valid session
    			throw new ResourceNotFoundException("invalid user");
        } else {
            // No valid session
			throw new ResourceNotFoundException("invalid user");

        }
	}
	
	public static void 	authenticate_ResModule(HttpServletRequest request, Long id) {
        HttpSession session = request.getSession(false); // Get the current session, but don't create a new one if it doesn't exist
        if (session != null && session.getAttribute("user") != null) {
            CustomerDTO currentUser = (CustomerDTO) session.getAttribute("user");
            // Check if the current user is authorized to modify the target user
            if(currentUser.getId().equals(id) || session.getAttribute("role") == Role.ADMIN || session.getAttribute("role") == Role.OWNER)
            	return;
            else 
                // No valid session
    			throw new ResourceNotFoundException("invalid user");
        } else {
            // No valid session
			throw new ResourceNotFoundException("invalid user");

        }
	}
	
}
