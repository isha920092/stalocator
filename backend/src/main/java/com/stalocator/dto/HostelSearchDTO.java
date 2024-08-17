package com.stalocator.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stalocator.entities.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HostelSearchDTO extends BaseDTO {

	@NotBlank
    private String name;

	@NotBlank
    private String description;

	@NotBlank
    private String address;

	@NotBlank
    private String facilities;

	
	    private Double price;
	    private Integer beds_available;
	    
//	    private Integer mixed_beds;
//	    private Integer male_beds;
//	@NotBlank
//	@JsonIgnore
//    private Owner owner;

}
