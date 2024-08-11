package com.stallocator.service;

import com.stallocator.dto.AuthDTO;
import com.stallocator.entities.Role;

public interface LoginService {

	Role findRole(AuthDTO dto);
}
