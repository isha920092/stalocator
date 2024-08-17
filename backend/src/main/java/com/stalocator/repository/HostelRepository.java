package com.stalocator.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.stalocator.dto.HostelDTO;
import com.stalocator.dto.HostelSearchDTO;
import com.stalocator.entities.Hostel;

public interface HostelRepository extends JpaRepository<Hostel, Long>{

	Collection<Hostel> findByOwnerId(@Param("ownerId") Long ownerId);

	List<Hostel> findByAddress(String address);

}
