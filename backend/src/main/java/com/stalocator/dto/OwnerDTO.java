package com.stalocator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.stalocator.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnerDTO extends UserDTO{
	
	private String firstName;
	
    private String lastName;
    
    private String email;
    
    private String password;
    
    @JsonProperty(access = Access.READ_ONLY)
    private Role role;
    
    private boolean approved;
    
    public OwnerDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        role = Role.CUSTOMER;
    }
}
