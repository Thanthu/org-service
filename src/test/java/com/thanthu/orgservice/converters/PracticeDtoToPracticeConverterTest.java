package com.thanthu.orgservice.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.Practice;

class PracticeDtoToPracticeConverterTest {
	
	private static final String NAME = "Test Practice Name";
	
	private PracticeDtoToPracticeConverter practiceDtoToPracticeConverter;

	@BeforeEach
	void setUp() throws Exception {
		practiceDtoToPracticeConverter = new PracticeDtoToPracticeConverter();
	}

	@Test
	void testConvert() {
		PracticeDto practiceDto = PracticeDto.builder().name(NAME).build();
		Practice practice = practiceDtoToPracticeConverter.convert(practiceDto);
		
		assertNull(practice.getId());
		assertEquals(NAME, practice.getName());
	}
	
	@Test
	void testConvertNull() {
		Practice practice = practiceDtoToPracticeConverter.convert(null);
		assertNull(practice);
	}

}
