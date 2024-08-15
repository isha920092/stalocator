package com.stallocator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stallocator.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

	Optional<Admin>  findByEmailAndPassword(String email, String pwd);

	boolean existsByEmail(String email);

}