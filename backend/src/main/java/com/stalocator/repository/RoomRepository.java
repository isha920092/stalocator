package com.stalocator.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.stalocator.dto.RoomDTO;
import com.stalocator.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	Collection<Room> findByHostelId(@Param("hostelId") Long hostelId);

}
