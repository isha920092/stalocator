package com.stalocator.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthDTO {

	@NotEmpty
	@Email(message = "Invalid email format")
	private String email;

	@NotEmpty(message = "Password must be supplied!!!")
	private String pwd;
}