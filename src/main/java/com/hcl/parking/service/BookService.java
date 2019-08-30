package com.hcl.parking.service;

import com.hcl.parking.dto.BookRequestDto;
import com.hcl.parking.dto.BookResponseDto;
import com.hcl.parking.dto.SelectSlotDto;

public interface BookService {
	
	BookResponseDto selectSlot(SelectSlotDto selectSlotDto);
	
	BookResponseDto bookSlot(BookRequestDto bookRequestDto);

}
