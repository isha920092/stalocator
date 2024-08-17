package com.stalocator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stalocator.dto.OwnerDTO;
import com.stalocator.entities.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
	
	Optional<Owner> findByEmailAndPassword(String em,String pass);
	
	Optional<Owner> findByEmail(String em);

	boolean existsByEmail(String em);

	void deleteByEmail(String em);
	
	List<Owner> findByApproved(boolean approval);

}
