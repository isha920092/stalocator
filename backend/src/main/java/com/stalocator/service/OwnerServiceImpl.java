package com.stalocator.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.AuthDTO;
import com.stalocator.dto.OwnerDTO;
import com.stalocator.dto.OwnerRequestDTO;
import com.stalocator.entities.Owner;
import com.stalocator.entities.Role;
import com.stalocator.repository.AdminRepository;
import com.stalocator.repository.CustomerRepository;
import com.stalocator.repository.OwnerRepository;

@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {
	
	@Autowired
	ModelMapper modelMapper;
	
	 @Autowired
	    private CustomerRepository customerRepository;
	    
	    @Autowired
	    private AdminRepository adminRepository;
	    
	    @Autowired
	    private OwnerRepository ownerRepository;

	@Override
	public List<OwnerDTO> getAllOwners() {
		return ownerRepository.findAll().stream()
		.map(owner -> (modelMapper.map(owner, OwnerDTO.class)))
		.collect(Collectors.toList());
	}

	@Override
	public OwnerDTO getOwner(String email) {
		Owner owner = ownerRepository.findByEmail(email)
		.orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
		return modelMapper.map(owner, OwnerDTO.class);
	}

	@Override
	public ApiResponse addOwner(OwnerDTO ownerDTO) {
//		if(isEmailInUse(ownerDTO.getEmail()))
//			return new ApiResponse("Account with this email already exists. Try logging in");
		Owner owner = modelMapper.map(ownerDTO, Owner.class);
		owner.setRole(Role.OWNER);
		ownerRepository.save(owner);
		return new ApiResponse("owner created");
	}

	@Override
	public ApiResponse updateOwner(String email, OwnerRequestDTO ownerRequestDTO) {
		Owner existingOwner = ownerRepository.findByEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
	    modelMapper.map(ownerRequestDTO, existingOwner);
	    existingOwner.setRole(existingOwner.getRole());
	    existingOwner.setEmail(email);
	    ownerRepository.save(existingOwner);
	    return new ApiResponse("Customer updated");
	}

	@Override
	public ApiResponse deleteOwner(String email) {
		if(ownerRepository.existsByEmail(email)) {
			ownerRepository.deleteByEmail(email);
			return new ApiResponse("owner deleted");
		}
		else throw new ResourceNotFoundException("Owner not found");
	}

	@Override
	public OwnerDTO authenticateUser(AuthDTO dto) {
		Owner owner = ownerRepository.
				findByEmailAndPassword(dto.getEmail(), dto.getPwd())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Authentication"));
		
		 if (!owner.isApproved()) {
		        throw new ResourceNotFoundException("Account not approved");
		    }
		
				return modelMapper.map(owner, OwnerDTO.class);
	}
	public boolean isEmailInUse(String email) {
	    return customerRepository.existsByEmail(email)||
	           adminRepository.existsByEmail(email)||
	           ownerRepository.existsByEmail(email);
	}


}
