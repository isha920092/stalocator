package com.stalocator.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stalocator.entities.Hostel;
import com.stalocator.entities.TypeDorm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {

	@NotBlank
    private int numberBeds;

	@NotBlank
    private TypeDorm typeDorm;

    private int availableBeds;
    
    private int reservedBeds;
    
//    @NotBlank
    @JsonIgnore
    private Hostel hostel;

}
