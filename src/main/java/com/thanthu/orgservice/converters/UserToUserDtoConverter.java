package com.thanthu.orgservice.converters;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.User;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {
	
	private static final PracticeToPracticeDtoConverter PRACTICE_TO_PRACTICE_DTO_CONVERTER = new PracticeToPracticeDtoConverter();

	@Override
	public UserDto convert(User user) {
		if (user == null) return null;
		
		return UserDto.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.createdDateTime(user.getCreatedDateTime())
				.updateDateTime(user.getUpdateDateTime())
				.type(user.getType())
				.build();
	}
	
	public UserDto convertWithPractices(User user) {
		if (user == null) return null;
		
		Set<PracticeDto> practiceDtos = user.getPractices().stream()
				.map(practice -> PRACTICE_TO_PRACTICE_DTO_CONVERTER.convert(practice))
				.collect(Collectors.toSet());
		
		return UserDto.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.createdDateTime(user.getCreatedDateTime())
				.updateDateTime(user.getUpdateDateTime())
				.type(user.getType())
				.practices(practiceDtos)
				.build();
	}
	
	public UserDto convertWithPracticesAndOrganization(User user) {
		if (user == null) return null;
		
		Set<PracticeDto> practiceDtos = user.getPractices().stream()
				.map(practice -> PRACTICE_TO_PRACTICE_DTO_CONVERTER.convertWithOrganization(practice))
				.collect(Collectors.toSet());
		
		return UserDto.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.createdDateTime(user.getCreatedDateTime())
				.updateDateTime(user.getUpdateDateTime())
				.type(user.getType())
				.practices(practiceDtos)
				.build();
	}

}
