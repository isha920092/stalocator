package com.stalocator.service;

import static com.stalocator.utils.Validations.validateDates;

import java.time.LocalDate;
import java.util.HashMap;
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
import com.stalocator.dto.ReservationDTO;
import com.stalocator.dto.ReservationRequestDTO;
import com.stalocator.dto.ReservationSearchDTO;
import com.stalocator.entities.Customer;
import com.stalocator.entities.Hostel;
import com.stalocator.entities.Reservation;
import com.stalocator.entities.TypeDorm;
import com.stalocator.repository.ReservationRepository;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<ReservationDTO> getCurrentReservations() {
		return reservationRepository.findAll().stream().filter(res->res.getBookingEndDate().isAfter(LocalDate.now().minusDays(1))).map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}
	
	@Override
	public List<ReservationDTO> getPastReservations() {
		return reservationRepository.findAll().stream().filter(res->res.getBookingEndDate().isBefore(LocalDate.now())).map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}

	@Override
	public ReservationDTO getReservation(Long id) {
		Reservation res = reservationRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
	    return modelMapper.map(res, ReservationDTO.class);
	}

	
	@Override
	public ApiResponse addReservation(Long custid,ReservationRequestDTO reservationRequestDTO) {
		validateDates(reservationRequestDTO.getBookingStartDate(), reservationRequestDTO.getBookingEndDate());
		Reservation res =modelMapper.map(reservationRequestDTO, Reservation.class);
		Customer customer = entityManager.getReference(Customer.class, custid);
	    Hostel hostel = entityManager.getReference(Hostel.class, reservationRequestDTO.getHostelId());
	    res.setCustomer(customer);
	    res.setHostel(hostel);
		reservationRepository.save(res);
		return new ApiResponse("reservation created");
	}

	@Override
	public ApiResponse updateReservation(Long custid,Long resid,ReservationRequestDTO reservationRequestDTO) {
	/*	Optional<Reservation> prev_res=reservationRepository.findById(id);*/
		validateDates(reservationRequestDTO.getBookingStartDate(), reservationRequestDTO.getBookingEndDate());
		Reservation prev_res=reservationRepository.findById(resid).get();
		if(reservationRepository.existsById(resid) && prev_res.getCustomer().getId()==custid) {
			Reservation res =modelMapper.map(reservationRequestDTO, Reservation.class);
			Customer customer = entityManager.getReference(Customer.class,prev_res.getCustomer().getId() );
		    Hostel hostel = entityManager.getReference(Hostel.class, reservationRequestDTO.getHostelId());
		    res.setCustomer(customer);
		    res.setHostel(hostel);
			res.setId(resid);
			res=reservationRepository.save(res);
		return new ApiResponse("Reservation updated");
		}
		else throw new ResourceNotFoundException("Reservation not found");
	}

	@Override
	public ApiResponse deleteReservation(Long custid,Long resid) {
		Reservation prev_res=reservationRepository.findById(resid).get();
		if(reservationRepository.existsById(resid) && prev_res.getCustomer().getId()==custid) {
			reservationRepository.deleteById(resid);
		return new ApiResponse("Reservation deleted");
		}
		else throw new ResourceNotFoundException("Reservation not found");
	}

	@Override
	public List<ReservationSearchDTO> getAllCurrentReservationsByCustomer(Long custid) {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.typeMap(Reservation.class, ReservationSearchDTO.class)
		    .addMappings(mapper -> mapper.map(src -> src.getHostel().getName(), ReservationSearchDTO::setHostelname));
		return reservationRepository.findByCustomerId(custid).stream().filter(res->res.getBookingEndDate().isAfter(LocalDate.now().minusDays(1))).sorted((res1,res2)->res1.getBookingStartDate().compareTo(res2.getBookingStartDate())).map(res->(modelMapper.map(res, ReservationSearchDTO.class))).collect(Collectors.toList());

	}
	
	@Override
	public List<ReservationDTO> getAllPastReservationsByCustomer(Long custid) {
		return reservationRepository.findByCustomerId(custid).stream().filter(res->res.getBookingEndDate().isBefore(LocalDate.now())).sorted((res1,res2)->res1.getBookingStartDate().compareTo(res2.getBookingStartDate())).map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}

	@Override
	public List<ReservationDTO> getAllReservationsByHostel(Long hostelID) {
		return reservationRepository.findByHostelId(hostelID).stream().map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}
	
	@Override
	public  Map<TypeDorm, Long> getHostelReservedBedsByDuration(Long hostelID, LocalDate bookingStartDate, LocalDate bookingEndDate) {
		List<Object[]> results = reservationRepository.countReservedBedsByHostelAndDuration(hostelID, bookingStartDate, bookingEndDate);
        Map<TypeDorm, Long> bedsByDormType = new HashMap<>();

        for (Object[] result : results) {
        	TypeDorm dormType = (TypeDorm) result[0]; // Enum type
        	Long totalBeds = (Long) result[1];
            bedsByDormType.put(dormType, totalBeds);
        }

        return bedsByDormType;
    }

}
