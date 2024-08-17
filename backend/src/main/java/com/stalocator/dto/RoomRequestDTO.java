package com.stalocator.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stalocator.entities.Hostel;
import com.stalocator.entities.TypeDorm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomRequestDTO {

    private int numberBeds;

    private TypeDorm typeDorm;

    private int availableBeds;
    
    private int reservedBeds;
    
    @JsonIgnore
    private Hostel hostel;
    
}
