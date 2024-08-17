package com.stalocator.entities;

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
@Table(name = "hostels")
public class Hostel extends BaseEntity{

	@Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address", nullable = false)
    private String address;
    
    @Column(name = "price")
    private Double price;

    @Column(name = "facilities")
    private String facilities;
    
    @Column(name = "female_beds")
    private Integer female_beds;
    
    @Column(name = "mixed_beds")
    private Integer mixed_beds;
    
    @Column(name = "male_beds")
    private Integer male_beds;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;
    
    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
    
    public void addRoom(Room room) {
    	rooms.add(room);
    	room.setHostel(this);
    }
    
    public void remooveRoom(Room room) {
    	rooms.remove(room);
    	room.setHostel(null);
    }
    
    public void addReservation(Reservation reservation) {
    	reservations.add(reservation);
    	reservation.setHostel(this);
    }
    
    public void removeReservation(Reservation reservation) {
    	reservations.remove(reservation);
    	reservation.setHostel(null);
    }
}
