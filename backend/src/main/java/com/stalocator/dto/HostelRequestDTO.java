package com.stalocator.dto;

import com.stalocator.entities.Owner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HostelRequestDTO extends BaseDTO {
	
    private String name;

    private String description;

    private String address;

    private String facilities;
//    private Double price;
//    private Integer female_beds;
//    
//    private Integer mixed_beds;
//    private Integer male_beds;

//    private Long owner;

}
