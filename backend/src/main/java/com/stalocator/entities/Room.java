package com.stalocator.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity{
	
	@Column(name = "number_beds", nullable = false)
    private int numberBeds;

	@Enumerated(EnumType.STRING)
    @Column(name = "type_dorm", nullable = false)
    private TypeDorm typeDorm;

    @Column(name = "available_beds")
    private int availableBeds;
    
    @Column(name = "reserved_beds")
    private int reservedBeds;
    
    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

}
