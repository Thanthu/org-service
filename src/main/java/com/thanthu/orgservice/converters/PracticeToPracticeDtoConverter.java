package com.thanthu.orgservice.converters;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.Practice;

@Component
public class PracticeToPracticeDtoConverter implements Converter<Practice, PracticeDto> {
	
	private static final OrganizationToOrganizationDtoConverter ORGANIZATION_TO_ORGANIZATION_DTO_CONVERTER = new OrganizationToOrganizationDtoConverter();
	
	private static final UserToUserDtoConverter USER_TO_USER_DTO_CONVERTER = new UserToUserDtoConverter();

	@Override
	public PracticeDto convert(Practice practice) {
		if (practice == null) return null;
		
		return PracticeDto.builder()
				.id(practice.getId())
				.name(practice.getName())
				.createdDateTime(practice.getCreatedDateTime())
				.updateDateTime(practice.getUpdateDateTime())
				.build();
	}
	
	public PracticeDto convertWithOrganization(Practice practice) {
		if (practice == null) return null;
		
		return PracticeDto.builder()
				.id(practice.getId())
				.name(practice.getName())
				.createdDateTime(practice.getCreatedDateTime())
				.updateDateTime(practice.getUpdateDateTime())
				.organization(ORGANIZATION_TO_ORGANIZATION_DTO_CONVERTER.convert(practice.getOrganization()))
				.build();
	}
	
	public PracticeDto convertWithUsers(Practice practice) {
		if (practice == null) return null;
		
		Set<UserDto> userDtos = practice.getUsers().stream()
				.map(user -> USER_TO_USER_DTO_CONVERTER.convert(user))
				.collect(Collectors.toSet());
		
		return PracticeDto.builder()
				.id(practice.getId())
				.name(practice.getName())
				.createdDateTime(practice.getCreatedDateTime())
				.updateDateTime(practice.getUpdateDateTime())
				.users(userDtos)
				.build();
	}
	
	public PracticeDto convertWithUsersAndOrganization(Practice practice) {
		if (practice == null) return null;
		
		Set<UserDto> userDtos = practice.getUsers().stream()
				.map(user -> USER_TO_USER_DTO_CONVERTER.convert(user))
				.collect(Collectors.toSet());
		
		return PracticeDto.builder()
				.id(practice.getId())
				.name(practice.getName())
				.createdDateTime(practice.getCreatedDateTime())
				.updateDateTime(practice.getUpdateDateTime())
				.users(userDtos)
				.organization(ORGANIZATION_TO_ORGANIZATION_DTO_CONVERTER.convert(practice.getOrganization()))
				.build();
	}

}
