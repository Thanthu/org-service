package com.thanthu.orgservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.model.Organization;

@Component
public class OrganizationDtoToOrganizationConverter implements Converter<OrganizationDto, Organization> {
	
	@Override
	public Organization convert(OrganizationDto organizationDto) {
		if(organizationDto == null) return null;
		
		return Organization.builder()
				.id(organizationDto.getId())
				.name(organizationDto.getName())
				.createdDateTime(organizationDto.getCreatedDateTime())
				.updateDateTime(organizationDto.getUpdateDateTime())
				.build();
	}
	
}
