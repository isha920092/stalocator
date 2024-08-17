package com.stalocator.service;

import com.stalocator.dto.AuthDTO;
import com.stalocator.entities.Role;

public interface LoginService {
	
	Role findRole(AuthDTO dto);
	
}
