package com.stallocator.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @Column(name = "number_beds")
    private int numberBeds;

    @Column(name = "type_dorm")
    private TypeDorm typeDorm;

    @Column(name = "availability")
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Bed> beds;
}
