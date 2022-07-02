package com.thanthu.orgservice.services;

import java.util.List;
import java.util.Set;

import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.dtos.PracticeDto;

public interface OrganizationService {
	
public OrganizationDto createOrganization(OrganizationDto organizationDto);
	
	public Set<OrganizationDto> getOrganizations(List<Long> ids, boolean showPractices);

	public OrganizationDto updateName(OrganizationDto organizationDto);
	
	public Set<OrganizationDto> findOrganizationsByName(String name, boolean showPractices);

	public OrganizationDto findOrganizationById(Long id, boolean showPractices);

	public OrganizationDto deleteOrganization(Long id);

	public PracticeDto createPractice(Long id, PracticeDto practiceDto);

}
