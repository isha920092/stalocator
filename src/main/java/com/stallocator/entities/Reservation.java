package com.stallocator.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{

	@Column(name = "booking_start_date")
	@NotNull
	private LocalDate bookingStartDate;

	@Column(name = "booking_end_date")
	@NotNull
	private LocalDate bookingEndDate;
	    
	@Column(name = "no_of_beds")
	@NotNull
	@Min(value = 1)
	private Integer beds;
	    
	@Column(name = "type_dorm")
	@NotNull
	private TypeDorm typeDorm;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@NotNull
	private Customer customer;
	    
	@ManyToOne
	@JoinColumn(name = "hostel_id")
	@NotNull
	private Hostel hostel;

//    @ManyToOne
//    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
//    private Room room;
}