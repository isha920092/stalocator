package com.stallocator.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "reservations")
public class Reservation extends BaseEntity{

    @Column(name = "booking_start_date")
    private Date bookingStartDate;

    @Column(name = "booking_end_date")
    private Date bookingEndDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

//    @ManyToOne
//    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
//    private Room room;
}