package com.hcl.parking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.parking.dto.SlotsResponseDto;
import com.hcl.parking.entity.AvailableSlot;
import com.hcl.parking.entity.Slot;
import com.hcl.parking.exception.CommonException;
import com.hcl.parking.repository.AvailableSlotRepository;
import com.hcl.parking.repository.SlotRepository;
import com.hcl.parking.util.ParkingConstants;

@Service
public class ShowSlotsServiceImpl implements ShowSlotsService {

	@Autowired
	AvailableSlotRepository availableSlotRepository;

	private static final Logger logger = LoggerFactory.getLogger(ShowSlotsServiceImpl.class);

	@Override
	public List<SlotsResponseDto> getAllAvailableSlots() {
		logger.info("inside the getAllAvailableSlots method..");
		List<SlotsResponseDto> responseList = new ArrayList<>();
		List<AvailableSlot> listslot = availableSlotRepository.findAll();
		if (listslot.isEmpty())
			throw new CommonException(ParkingConstants.SLOTS_NOT_AVAILABLE);

		listslot.stream().forEach(a -> {
			SlotsResponseDto slotsResponseDto = new SlotsResponseDto();
			slotsResponseDto.setAvailableDate(listslot.get(0).getAvailableDate());
			slotsResponseDto.setAvailableSlotId(listslot.get(0).getAvailableSlotId());
			slotsResponseDto.setBookedEmpId(listslot.get(0).getBookedEmpId());
			slotsResponseDto.setSlotId(listslot.get(0).getSlotId());
			slotsResponseDto.setStatus(listslot.get(0).getStatus());
			slotsResponseDto.setVipId(listslot.get(0).getVipId());
			BeanUtils.copyProperties(a, slotsResponseDto);
			responseList.add(slotsResponseDto);

		});

		return responseList;
	}

}
