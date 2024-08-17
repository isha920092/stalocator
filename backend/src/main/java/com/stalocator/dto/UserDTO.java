package com.stalocator.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.stalocator.entities.Role;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UserDTO {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDate creationDate;
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime updationTimeStamp;
	@JsonProperty(access = Access.READ_ONLY)
	private String token;
	@JsonProperty(access = Access.READ_ONLY)
	private Role role;

}
