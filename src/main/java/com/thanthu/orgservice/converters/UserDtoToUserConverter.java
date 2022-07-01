package com.thanthu.orgservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.model.User;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

	@Override
	public User convert(UserDto userDto) {
		if (userDto == null) return null;
		
		return User.builder()
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.type(userDto.getType())
				.build();
	}

}
