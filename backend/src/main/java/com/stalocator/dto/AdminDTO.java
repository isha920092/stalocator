package com.stalocator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO extends UserDTO{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}