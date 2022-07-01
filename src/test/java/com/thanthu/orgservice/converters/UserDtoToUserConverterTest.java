package com.thanthu.orgservice.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.enums.UserType;
import com.thanthu.orgservice.model.User;

class UserDtoToUserConverterTest {

	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final UserType USER_TYPE = UserType.DOCTOR;
	
	
	private UserDtoToUserConverter userDtoToUserConverter;
	
	@BeforeEach
	void setUp() throws Exception {
		userDtoToUserConverter = new UserDtoToUserConverter();
	}

	@Test
	void testConvert() {
		UserDto userDto = UserDto.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.type(UserType.DOCTOR)
				.build();
		User user = userDtoToUserConverter.convert(userDto);
		
		assertNull(user.getId());
		assertEquals(FIRST_NAME, user.getFirstName());
		assertEquals(LAST_NAME, user.getLastName());
		assertEquals(USER_TYPE, user.getType());
	}
	
	@Test
	void testConvertNull() {
		User user = userDtoToUserConverter.convert(null);
		assertNull(user);
	}

}
