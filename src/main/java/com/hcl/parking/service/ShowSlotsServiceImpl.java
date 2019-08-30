package com.hcl.parking.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.parking.dto.SlotsResponseDto;
import com.hcl.parking.entity.AvailableSlot;
import com.hcl.parking.repository.AvailableSlotRepository;


/**
 * @author Gurpreet Singh
 *
 */
@Service
public class ShowSlotsServiceImpl implements ShowSlotsService {

	@Autowired
	AvailableSlotRepository availableSlotRepository;

	private static final Logger logger = LoggerFactory.getLogger(ShowSlotsServiceImpl.class);

	@Override
	public List<SlotsResponseDto> getAllAvailableSlots() {
		logger.info("inside the getAllAvailableSlots method..");
		List<SlotsResponseDto> listslot = new ArrayList<>();
		LocalDate availableDate = LocalDate.now();
		List<AvailableSlot> listAvailable = availableSlotRepository.findAll();
		listAvailable.stream().forEach(l->
		{
			if(l.getAvailableDate().equals(availableDate))
			{
				SlotsResponseDto slotsResponseDto = new SlotsResponseDto();
				BeanUtils.copyProperties(l, slotsResponseDto);
				listslot.add(slotsResponseDto);
			}
		});
		
		return listslot;
	}


}
