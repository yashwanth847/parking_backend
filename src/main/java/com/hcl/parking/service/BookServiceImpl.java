package com.hcl.parking.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.parking.dto.BookRequestDto;
import com.hcl.parking.dto.BookResponseDto;
import com.hcl.parking.dto.SelectSlotDto;
import com.hcl.parking.entity.AvailableSlot;
import com.hcl.parking.entity.Registration;
import com.hcl.parking.entity.RequestSlot;
import com.hcl.parking.exception.CommonException;
import com.hcl.parking.repository.AvailableSlotRepository;
import com.hcl.parking.repository.RegistrationRepository;
import com.hcl.parking.repository.RequestSlotRepository;
import com.hcl.parking.util.ParkingConstants;

/**
 * 
 * @author HAriPriya G
 *
 */

@Service
public class BookServiceImpl implements BookService {
	private static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired
	RegistrationRepository registrationRepository;

	@Autowired
	RequestSlotRepository requestSlotRepository;

	@Autowired
	AvailableSlotRepository availableSlotRepository;

	/**
	 * 
	 * This method is intended to book the slots based on the current date
	 * 
	 * @param regId is the input request which includes
	 * @return it returns BookResponseDto object with message and bookingId
	 */

	@Override
	public BookResponseDto bookSlot(BookRequestDto bookRequestDto) {

		logger.info("request slot service impl");
		Optional<Registration> registration = registrationRepository.findById(bookRequestDto.getRegId());
		if (!registration.isPresent())
			throw new CommonException(ParkingConstants.EMPLOYEE_NOT_FOUND);
		
		Optional<AvailableSlot> availableSlot= availableSlotRepository.findById(bookRequestDto.getAvailableSlotId());
		if (!availableSlot.isPresent())
			throw new CommonException("No slot present");		

		// Fetching all availableSlots from AvailableSlot table
		List<AvailableSlot> availableSlots = availableSlotRepository.findByAvailableDate(LocalDate.now());
		if (availableSlots.isEmpty())
			throw new CommonException(ParkingConstants.NO_AVAILABLE_SLOTS);

		AvailableSlot availableSlotDb = availableSlots.get(0);
		availableSlotDb.setBookedEmpId(bookRequestDto.getRegId());
		availableSlotDb.setStatus("booked");
		availableSlotRepository.save(availableSlotDb);

		// saving all requests in RequestSlot table
		RequestSlot requestSlot = new RequestSlot();
		requestSlot.setRequestDate(LocalDate.now());
		requestSlot.setRegistrationId(bookRequestDto.getRegId());
		requestSlotRepository.save(requestSlot);

		return new BookResponseDto("slot booked");
	}

	public LocalDate getLocalDate(String data) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ParkingConstants.DATE_FORMAT);
		LocalDate dateOfJoiningLocalDate = LocalDate.parse(data, dateTimeFormatter);
		return dateOfJoiningLocalDate;

	}

	@Transactional(isolation=Isolation.REPEATABLE_READ)
	@Override
	public BookResponseDto selectSlot(SelectSlotDto selectSlotDto) {
		
		AvailableSlot lockAvailableSlot = availableSlotRepository
				.lockSlot(selectSlotDto.getAvailableSlotId());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		availableSlotRepository.save(lockAvailableSlot);

		return new BookResponseDto("Slot locked");
	}

}
