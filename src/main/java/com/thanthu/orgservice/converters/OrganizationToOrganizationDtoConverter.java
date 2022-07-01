package com.thanthu.orgservice.converters;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.Organization;

@Component
public class OrganizationToOrganizationDtoConverter implements Converter<Organization, OrganizationDto> {
	
	private static final PracticeToPracticeDtoConverter PRACTICE_TO_PRACTICE_DTO_CONVERTER = new PracticeToPracticeDtoConverter();

	@Override
	public OrganizationDto convert(Organization organization) {
		if(organization == null) return null;
		
		return OrganizationDto.builder()
				.id(organization.getId())
				.name(organization.getName())
				.createdDateTime(organization.getCreatedDateTime())
				.updateDateTime(organization.getUpdateDateTime())
				.build();
	}
	
	public OrganizationDto convertWithPractices(Organization organization) {
		if(organization == null) return null;
		
		Set<PracticeDto> practiceDtos = organization.getPractices().stream()
				.map(practice -> PRACTICE_TO_PRACTICE_DTO_CONVERTER.convert(practice))
				.collect(Collectors.toSet());
		
		return OrganizationDto.builder()
				.id(organization.getId())
				.name(organization.getName())
				.createdDateTime(organization.getCreatedDateTime())
				.updateDateTime(organization.getUpdateDateTime())
				.practices(practiceDtos)
				.build();
	}

}
