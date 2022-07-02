package com.thanthu.orgservice.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.thanthu.orgservice.converters.OrganizationDtoToOrganizationConverter;
import com.thanthu.orgservice.converters.OrganizationToOrganizationDtoConverter;
import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.exceptions.NotFoundException;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.repositories.OrganizationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	private final OrganizationDtoToOrganizationConverter organizationDtoToOrganizationConverter;
	
	private final OrganizationToOrganizationDtoConverter organizationToOrganizationDtoConverter;
	
	private final OrganizationRepository organizationRepository;
	
	private final PracticeService practiceService;
	
	@Override
	public OrganizationDto createOrganization(OrganizationDto organizationDto) {
		Organization organization = organizationDtoToOrganizationConverter.convert(organizationDto);
		Organization savedOrganization = saveOrganization(organization);
		return organizationToOrganizationDtoConverter.convert(savedOrganization);
	}

	@Override
	public Set<OrganizationDto> getOrganizations(List<Long> ids, boolean showPractices) {
		List<Organization> organizations = organizationRepository.findAllById(ids);
		return organizationsToOrganizationDtos(organizations, showPractices);
	}

	@Override
	public OrganizationDto updateName(OrganizationDto organizationDto) {
		Organization organization = findById(organizationDto.getId());
		organization.setName(organizationDto.getName());
		Organization savedOrganization = saveOrganization(organization);
		return organizationToOrganizationDtoConverter.convert(savedOrganization);
	}

	@Override
	public Set<OrganizationDto> findOrganizationsByName(String name, boolean showPractices) {
		List<Organization> organizations = organizationRepository.findAllByName(name.toLowerCase().trim());
		return organizationsToOrganizationDtos(organizations, showPractices);
	}

	@Override
	public OrganizationDto findOrganizationById(Long id, boolean showPractices) {
		Organization organization = findById(id);
		if(showPractices) {
			return organizationToOrganizationDtoConverter.convertWithPractices(organization);
		}
		return organizationToOrganizationDtoConverter.convert(organization);
	}
	
	private Organization saveOrganization(Organization organization) {
		return organizationRepository.save(organization);
	}
	
	private Organization findById(Long id) {
		return organizationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Organization not found."));
	}
	
	private Set<OrganizationDto> organizationsToOrganizationDtos(List<Organization> organizations, boolean showPractices) {
		if(showPractices) {
			return organizations.stream()
					.map(organization -> organizationToOrganizationDtoConverter.convertWithPractices(organization))
					.collect(Collectors.toSet());
		}
		return organizations.stream()
				.map(organization -> organizationToOrganizationDtoConverter.convert(organization))
				.collect(Collectors.toSet());
	}

	@Override
	public OrganizationDto deleteOrganization(Long id) {
		Organization organization = findById(id);
		OrganizationDto organizationDto = organizationToOrganizationDtoConverter.convert(organization);
		deleteOrganization(organization);
		return organizationDto;
	}
	
	private void deleteOrganization(Organization organization) {
		organizationRepository.delete(organization);
	}

	@Override
	public PracticeDto createPractice(Long id, PracticeDto practiceDto) {
		Organization organization = findById(id);
		return practiceService.createPractice(practiceDto, organization);
	}

}
