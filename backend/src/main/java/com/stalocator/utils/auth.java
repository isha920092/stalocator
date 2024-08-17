package com.stalocator.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.dto.CustomerDTO;
import com.stalocator.dto.OwnerDTO;
import com.stalocator.entities.Role;

public class auth {
	public static void authenticate_CustModule(HttpServletRequest request, String em) {
	    HttpSession session = request.getSession(false); // Get the current session, but don't create a new one if it doesn't exist
	    if (session != null && session.getAttribute("user") != null) {
	        Object user = session.getAttribute("user");

	        if (user instanceof CustomerDTO) {
	            CustomerDTO currentUser = (CustomerDTO) user;
	            if(currentUser.getEmail().equals(em) || session.getAttribute("role") == Role.ADMIN)
	                return;
	        }
	        // No valid session or role mismatch
	        throw new ResourceNotFoundException("invalid user");
	    } else {
	        // No valid session
	        throw new ResourceNotFoundException("invalid user");
	    }
	}

	public static void authenticate_OwnerModule(HttpServletRequest request, String email) {
	    HttpSession session = request.getSession(false); // Get the current session, but don't create a new one if it doesn't exist
	    if (session != null && session.getAttribute("user") != null) {
	        Object user = session.getAttribute("user");

	        if (user instanceof OwnerDTO) {
	            OwnerDTO currentUser = (OwnerDTO) user;
	            if(currentUser.getEmail().equals(email) || session.getAttribute("role") == Role.ADMIN)
	                return;
	        }
	        // No valid session or role mismatch
	        throw new ResourceNotFoundException("invalid user");
	    } else {
	        // No valid session
	        throw new ResourceNotFoundException("invalid user");
	    }
	}

	
	public static void 	authenticate_AdminModule(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Get the current session, but don't create a new one if it doesn't exist
        if (session != null && session.getAttribute("user") != null) {
            if(session.getAttribute("role") == Role.ADMIN )
            	return;
            else 
                // No valid session
    			throw new ResourceNotFoundException("invalid user");
        } else {
            // No valid session
			throw new ResourceNotFoundException("invalid user");

        }
	}
	
	public static void 	authenticate_ResModule(HttpServletRequest request, String tok) {
		String authorizationHeader = request.getHeader("Authorization");
		String token = authorizationHeader.substring(7); 
		System.out.println(tok);
		System.out.println(authorizationHeader);
		if(token.equals(tok))
			return;
		else 
          // No valid session
			throw new ResourceNotFoundException("invalid user");
//        HttpSession session = request.getSession(false); // Get the current session, but don't create a new one if it doesn't exist
//        System.out.println("k");
//        System.out.println("a"+session);
//        if (session != null && session.getAttribute("user") != null) {
//            CustomerDTO currentUser = (CustomerDTO) session.getAttribute("user");
//            // Check if the current user is authorized to modify the target user
//            if(currentUser.getId().equals(id) || session.getAttribute("role") == Role.ADMIN || session.getAttribute("role") == Role.OWNER)
//            	return;
//            else 
//                // No valid session
//    			throw new ResourceNotFoundException("invalid user");
//        } else {
//            // No valid session
//			throw new ResourceNotFoundException("ainvalid user");
//
//        }
	}
	
	public static void authenticate_HostelAndRoomModule(HttpServletRequest request, Long id) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
        	OwnerDTO currentUser = (OwnerDTO) session.getAttribute("user");
        	if(currentUser.getId().equals(id) ||  session.getAttribute("role") == Role.ADMIN)
        		return;
        	else
        		throw new ResourceNotFoundException("invalid user");
        }
        else
        	throw new ResourceNotFoundException("invalid user");
	}
}