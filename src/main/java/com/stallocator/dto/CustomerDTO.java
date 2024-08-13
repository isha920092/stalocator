package com.stallocator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.stallocator.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends BaseDTO{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @JsonProperty(access = Access.READ_ONLY)
    private Role role;
    
    public CustomerDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        role = Role.CUSTOMER;
    }
//    private List<Reservation> reservations=new ArrayList<>();
}