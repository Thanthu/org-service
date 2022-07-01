package com.thanthu.orgservice.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.model.Practice;
import com.thanthu.orgservice.model.User;

class PracticeToPracticeDtoConverterTest {
	
	private static final Long ID = 1L;
	private static final String NAME = "Test Org Name";
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private Practice practice;
	
	private PracticeToPracticeDtoConverter practiceToPracticeDtoConverter;

	@BeforeEach
	void setUp() throws Exception {
		practiceToPracticeDtoConverter = new PracticeToPracticeDtoConverter();
		
		Set<User> users = new HashSet<User>();
		users.add(User.builder().id(1L).build());
		users.add(User.builder().id(2L).build());
		
		practice = Practice.builder()
				.id(ID)
				.name(NAME)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.organization(Organization.builder().id(1L).build())
				.users(users)
				.build();
	}

	@Test
	void testConvert() {
		PracticeDto practiceDto = practiceToPracticeDtoConverter.convert(practice);
		
		assertEquals(ID, practiceDto.getId());
		assertEquals(NAME, practiceDto.getName());
		assertEquals(DATE_TIME, practiceDto.getCreatedDateTime());
		assertEquals(DATE_TIME, practiceDto.getUpdateDateTime());
		assertNull(practiceDto.getOrganization());
		assertEquals(0, practiceDto.getUsers().size());
	}
	
	@Test
	void testConvertNull() {
		PracticeDto practiceDto = practiceToPracticeDtoConverter.convert(null);
		assertNull(practiceDto);
	}

	@Test
	void testConvertWithOrganization() {
		PracticeDto practiceDto = practiceToPracticeDtoConverter.convertWithOrganization(practice);
		
		assertEquals(ID, practiceDto.getId());
		assertEquals(NAME, practiceDto.getName());
		assertEquals(DATE_TIME, practiceDto.getCreatedDateTime());
		assertEquals(DATE_TIME, practiceDto.getUpdateDateTime());
		assertNotNull(practiceDto.getOrganization());
		assertEquals(0, practiceDto.getUsers().size());
	}
	
	@Test
	void testConvertWithOrganizationNull() {
		PracticeDto practiceDto = practiceToPracticeDtoConverter.convertWithOrganization(null);
		assertNull(practiceDto);
	}

	@Test
	void testConvertWithUsers() {
		PracticeDto practiceDto = practiceToPracticeDtoConverter.convertWithUsers(practice);
		
		assertEquals(ID, practiceDto.getId());
		assertEquals(NAME, practiceDto.getName());
		assertEquals(DATE_TIME, practiceDto.getCreatedDateTime());
		assertEquals(DATE_TIME, practiceDto.getUpdateDateTime());
		assertNull(practiceDto.getOrganization());
		assertEquals(2, practiceDto.getUsers().size());
	}
	
	@Test
	void testConvertWithUsersNull() {
		PracticeDto practiceDto = practiceToPracticeDtoConverter.convertWithUsers(null);
		assertNull(practiceDto);
	}

	@Test
	void testConvertWithUsersAndOrganization() {
		PracticeDto practiceDto = practiceToPracticeDtoConverter.convertWithUsersAndOrganization(practice);
		
		assertEquals(ID, practiceDto.getId());
		assertEquals(NAME, practiceDto.getName());
		assertEquals(DATE_TIME, practiceDto.getCreatedDateTime());
		assertEquals(DATE_TIME, practiceDto.getUpdateDateTime());
		assertNotNull(practiceDto.getOrganization());
		assertEquals(2, practiceDto.getUsers().size());
	}

}
