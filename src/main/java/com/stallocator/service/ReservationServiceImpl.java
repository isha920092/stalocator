package com.stallocator.service;

import static com.stallocator.utils.Validations.validateDates;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stallocator.custom_exc.ResourceNotFoundException;
import com.stallocator.dto.ApiResponse;
import com.stallocator.dto.ReservationDTO;
import com.stallocator.dto.ReservationRequestDTO;
import com.stallocator.entities.Customer;
import com.stallocator.entities.Hostel;
import com.stallocator.entities.Reservation;
import com.stallocator.repository.ReservationRepository;

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
	public List<ReservationDTO> getAllReservations() {
		return reservationRepository.findAll().stream().map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}

	@Override
	public ReservationDTO getReservation(Long id) {
		Reservation res = reservationRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
	    return modelMapper.map(res, ReservationDTO.class);
	}

	//WORK PENDING
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
	public List<ReservationDTO> getAllReservationsByCustomer(Long custid) {
		return reservationRepository.findByCustomerId(custid).stream().sorted((res1,res2)->res1.getBookingStartDate().compareTo(res2.getBookingStartDate())).map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}

	@Override
	public List<ReservationDTO> getAllReservationsByHostel(Long hostelID) {
		return reservationRepository.findByHostel(hostelID).stream().map(res->(modelMapper.map(res, ReservationDTO.class))).collect(Collectors.toList());

	}

}
