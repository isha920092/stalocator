package com.stallocator.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO extends BaseDTO{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
