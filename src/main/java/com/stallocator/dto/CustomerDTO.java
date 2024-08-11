package com.stallocator.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.stallocator.entities.Reservation;
import com.stallocator.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends BaseDTO{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    @JsonProperty(access = Access.READ_ONLY)
    private Role role;
    
    
//    private List<Reservation> reservations=new ArrayList<>();
}
