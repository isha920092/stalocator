package com.stalocator.service;

import static com.stalocator.utils.Validations.validateDates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stalocator.custom_exec.ResourceNotFoundException;
import com.stalocator.dto.ApiResponse;
import com.stalocator.dto.HostelAddDTO;
import com.stalocator.dto.HostelDTO;
import com.stalocator.dto.HostelRequestDTO;
import com.stalocator.dto.HostelSearchDTO;
import com.stalocator.dto.SearchDTO;
import com.stalocator.entities.Hostel;
import com.stalocator.entities.Owner;
import com.stalocator.entities.TypeDorm;
import com.stalocator.repository.HostelRepository;

@Service
@Transactional
public class HostelServiceImpl implements HostelService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private HostelRepository hostelRepository;
	
	@Autowired
	private ReservationServiceImpl reservationServiceImpl;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public HostelDTO getHostel(Long hostelId) {
		Hostel hostel = hostelRepository.findById(hostelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));
	    return modelMapper.map(hostel, HostelDTO.class);
	}

	@Override
	public ApiResponse addHostel(Long ownerId, HostelAddDTO hostelRequestDTO) {
	    Hostel hostel = modelMapper.map(hostelRequestDTO, Hostel.class);
	    
	    // Get reference for the owner entity
	    Owner owner = entityManager.getReference(Owner.class, ownerId);
	    
	    // Set the owner for the hostel
	    hostel.setOwner(owner);
	    
	    // Save the hostel entity
	    hostelRepository.save(hostel);
	    
	    // Return response
	    return new ApiResponse("Hostel created");
	}


	@Override
	public ApiResponse updateHostel(Long ownerId, Long hostelId, HostelRequestDTO hostelRequestDTO) {
	    Hostel originalHostel = hostelRepository.findById(hostelId)
	            .orElseThrow(() -> new ResourceNotFoundException("Hostel not found"));

	    // Check if the hostel belongs to the given owner
	    if (!originalHostel.getOwner().getId().equals(ownerId)) {
	        throw new ResourceNotFoundException("Hostel not found or owner mismatch");
	    }

	    // Update the existing hostel fields without creating a new instance
	    originalHostel.setName(hostelRequestDTO.getName());
	    originalHostel.setDescription(hostelRequestDTO.getDescription());
	    originalHostel.setAddress(hostelRequestDTO.getAddress());
	    originalHostel.setFacilities(hostelRequestDTO.getFacilities());

	    // Save the updated entity
	    hostelRepository.save(originalHostel);

	    return new ApiResponse("Hostel details updated");
	}


	@Override
	public ApiResponse deleteReservation(Long ownerId, Long hostelId) {
		Hostel hostel = hostelRepository.findById(hostelId).get();
		if(hostelRepository.existsById(hostelId) && hostel.getOwner().getId()==ownerId) {
			hostelRepository.deleteById(hostelId);
			return new ApiResponse("Hostel deleted");
		}
		else throw new ResourceNotFoundException("Hostel not found");
	}

	@Override
	public List<HostelDTO> getAllHostelsByOwner(Long ownerId) {
		return hostelRepository.findByOwnerId(ownerId).stream()
				.map(res->(modelMapper.map(res, HostelDTO.class))).collect(Collectors.toList());
	}	
	
	@Override
	public List<HostelSearchDTO> getHostelAvailability(SearchDTO search) {
	    // Fetch hostels based on the search address
		validateDates(search.getBookingStartDate(), search.getBookingEndDate());
	    List<Hostel> hostels = hostelRepository.findByAddress(search.getAddress());

	    List<HostelSearchDTO> availableHostels = new ArrayList<>();
	    
	    // Iterate over each hostel and check availability
	    for (Hostel hostel : hostels) {
	        // Get the reserved beds for the hostel in the specified duration
	        Map<TypeDorm, Long> reservedBeds = reservationServiceImpl.getHostelReservedBedsByDuration(
	            hostel.getId(), 
	            search.getBookingStartDate(), 
	            search.getBookingEndDate()
	        );

	        // Initialize HostelSearchDTO
	        HostelSearchDTO hostelDTO = new HostelSearchDTO();
	        hostelDTO.setId(hostel.getId());
	        hostelDTO.setName(hostel.getName());
	        hostelDTO.setDescription(hostel.getDescription());
	        hostelDTO.setAddress(hostel.getAddress());
	        hostelDTO.setFacilities(hostel.getFacilities());
	        hostelDTO.setPrice(hostel.getPrice());

	        // Initialize available beds for the specified dorm type
	        Integer availableBeds = 0;

	        TypeDorm typeDorm = search.getTypeDorm();
	        if (typeDorm != null) {
	            switch (typeDorm) {
	                case FEMALE:
	                    availableBeds = (hostel.getFemale_beds() != null ? hostel.getFemale_beds() : 0) - reservedBeds.getOrDefault(TypeDorm.FEMALE, Long.valueOf(0)).intValue();
	                    break;
	                case MALE:
	                    availableBeds = (hostel.getMale_beds() != null ? hostel.getMale_beds() : 0) - reservedBeds.getOrDefault(TypeDorm.MALE, Long.valueOf(0)).intValue();
	                    break;
	                case MIXED:
	                    availableBeds = (hostel.getMixed_beds() != null ? hostel.getMixed_beds() : 0) - reservedBeds.getOrDefault(TypeDorm.MIXED, Long.valueOf(0)).intValue();
	                    break;
	            }
	            hostelDTO.setBeds_available(availableBeds);
	        } else {
	            // If no specific dorm type is specified, consider all types
	            Integer totalAvailableBeds = 
	                ((hostel.getFemale_beds() != null ? hostel.getFemale_beds() : 0) - reservedBeds.getOrDefault(TypeDorm.FEMALE, Long.valueOf(0)).intValue()) +
	                ((hostel.getMale_beds() != null ? hostel.getMale_beds() : 0) - reservedBeds.getOrDefault(TypeDorm.MALE, Long.valueOf(0)).intValue()) +
	                ((hostel.getMixed_beds() != null ? hostel.getMixed_beds() : 0) - reservedBeds.getOrDefault(TypeDorm.MIXED, Long.valueOf(0)).intValue());
	            hostelDTO.setBeds_available(totalAvailableBeds);
	        }

	        // Check if the hostel meets the search criteria
	        boolean matchesCriteria = true;

	        if (search.getBeds() != null && availableBeds < search.getBeds()) {
	            matchesCriteria = false;
	        }

	        if (matchesCriteria) {
	            availableHostels.add(hostelDTO);
	        }
	    }

	    return availableHostels;
	}



		
}
