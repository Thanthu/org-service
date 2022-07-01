package com.thanthu.orgservice.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.enums.UserType;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.model.Practice;
import com.thanthu.orgservice.model.User;

class UserToUserDtoConverterTest {
	
	private static final Long ID = 1L;
	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final UserType USER_TYPE = UserType.DOCTOR;
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private User user;
	
	private UserToUserDtoConverter userToUserDtoConverter;

	@BeforeEach
	void setUp() throws Exception {
		userToUserDtoConverter = new UserToUserDtoConverter();
		
		Organization organization = Organization.builder().id(1L).build();
		
		Set<Practice> practices = new HashSet<>();
		practices.add(Practice.builder().id(1L).organization(organization).build());
		practices.add(Practice.builder().id(2L).organization(organization).build());
		
		user = User.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.type(USER_TYPE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.practices(practices)
				.build();
	}

	@Test
	void testConvert() {
		UserDto userDto = userToUserDtoConverter.convert(user);
		
		assertEquals(ID, userDto.getId());
		assertEquals(FIRST_NAME, userDto.getFirstName());
		assertEquals(LAST_NAME, userDto.getLastName());
		assertEquals(USER_TYPE, userDto.getType());
		assertEquals(DATE_TIME, userDto.getCreatedDateTime());
		assertEquals(DATE_TIME, userDto.getUpdateDateTime());
		assertEquals(0, userDto.getPractices().size());
	}
	
	@Test
	void testConvertNull() {
		UserDto userDto = userToUserDtoConverter.convert(null);
		assertNull(userDto);
	}

	@Test
	void testConvertWithPractices() {
		UserDto userDto = userToUserDtoConverter.convertWithPractices(user);
		
		assertEquals(ID, userDto.getId());
		assertEquals(FIRST_NAME, userDto.getFirstName());
		assertEquals(LAST_NAME, userDto.getLastName());
		assertEquals(USER_TYPE, userDto.getType());
		assertEquals(DATE_TIME, userDto.getCreatedDateTime());
		assertEquals(DATE_TIME, userDto.getUpdateDateTime());
		assertEquals(2, userDto.getPractices().size());
		assertNull(userDto.getPractices().iterator().next().getOrganization());
	}
	
	@Test
	void testConvertWithPracticesNull() {
		UserDto userDto = userToUserDtoConverter.convertWithPractices(null);
		assertNull(userDto);
	}

	@Test
	void testConvertWithPracticesAndOrganization() {
		UserDto userDto = userToUserDtoConverter.convertWithPracticesAndOrganization(user);
		
		assertEquals(ID, userDto.getId());
		assertEquals(FIRST_NAME, userDto.getFirstName());
		assertEquals(LAST_NAME, userDto.getLastName());
		assertEquals(USER_TYPE, userDto.getType());
		assertEquals(DATE_TIME, userDto.getCreatedDateTime());
		assertEquals(DATE_TIME, userDto.getUpdateDateTime());
		assertEquals(2, userDto.getPractices().size());
		assertNotNull(userDto.getPractices().iterator().next().getOrganization());
	}

}
