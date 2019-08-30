package com.hcl.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.parking.dto.BookRequestDto;
import com.hcl.parking.dto.BookResponseDto;
import com.hcl.parking.dto.SelectSlotDto;
import com.hcl.parking.service.BookService;

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/api")
public class BookSlotController {

	@Autowired
	BookService bookService;

	@PostMapping("/bookSlot")
	public ResponseEntity<BookResponseDto> bookSlots(@RequestBody BookRequestDto bookRequestDto) {
		return new ResponseEntity<>(bookService.bookSlot(bookRequestDto), HttpStatus.CREATED);
	}
	
	@PutMapping("/selectSlot")
	public ResponseEntity<BookResponseDto> selectSlot(@RequestBody SelectSlotDto selectSlotDto) {
		return new ResponseEntity<>(bookService.selectSlot(selectSlotDto), HttpStatus.CREATED);
	}

	
}
