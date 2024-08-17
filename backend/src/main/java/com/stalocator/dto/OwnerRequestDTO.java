package com.stalocator.dto;

import com.stalocator.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerRequestDTO {

	private String firstName;
	
    private String lastName;
    
    private String email;
    
    private String password;
    
    private Role role;
    
    private boolean approved;
    
    public OwnerRequestDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        role = Role.OWNER;
    }
}
