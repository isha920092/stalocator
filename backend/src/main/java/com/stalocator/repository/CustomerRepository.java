package com.stalocator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stalocator.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	Optional<Customer> findByEmailAndPassword(String em,String pass);

	Optional<Customer> findByEmail(String em);

	boolean existsByEmail(String em);

	void deleteByEmail(String em);
}