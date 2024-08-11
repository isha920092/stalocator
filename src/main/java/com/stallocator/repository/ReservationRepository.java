package com.stallocator.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallocator.dto.ReservationDTO;
import com.stallocator.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	Collection<Reservation> findByCustomerId(Long id);

	Collection<Reservation> findByHostel(Long hostelID);

}
