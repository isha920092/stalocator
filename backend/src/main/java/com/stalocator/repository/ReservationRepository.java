package com.stalocator.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stalocator.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

	Collection<Reservation> findByCustomerId(@Param("id") Long id);

	Collection<Reservation> findByHostelId(@Param("hostelID") Long hostelID);
	
	 @Query("SELECT r.typeDorm, SUM(r.beds) FROM Reservation r WHERE r.hostel.id = :hostelID AND " +
	           "((r.bookingStartDate <= :bookingEndDate AND r.bookingEndDate >= :bookingStartDate)) GROUP BY r.typeDorm")
	    List<Object[]> countReservedBedsByHostelAndDuration(
	        @Param("hostelID") Long hostelID,
	        @Param("bookingStartDate") LocalDate bookingStartDate,
	        @Param("bookingEndDate") LocalDate bookingEndDate
	    );

}
