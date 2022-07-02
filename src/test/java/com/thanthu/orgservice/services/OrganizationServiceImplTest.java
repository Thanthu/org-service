package com.thanthu.orgservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanthu.orgservice.converters.OrganizationDtoToOrganizationConverter;
import com.thanthu.orgservice.converters.OrganizationToOrganizationDtoConverter;
import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.model.Practice;
import com.thanthu.orgservice.repositories.OrganizationRepository;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {
	
	private static final Long ID = 1L;
	private static final String NAME = "Test Org Name";
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	
	private OrganizationDto organizationDto;
	private Organization organization;
	
	@Spy
	private OrganizationDtoToOrganizationConverter organizationDtoToOrganizationConverter;
	
	@Spy
	private OrganizationToOrganizationDtoConverter organizationToOrganizationDtoConverter;
	
	@Mock
	private OrganizationRepository organizationRepository;
	
	@Mock
	private PracticeService practiceService;
	
	@InjectMocks
	private OrganizationServiceImpl organizationService;
	
	@BeforeEach
	void setUp() throws Exception {
		organizationDto = OrganizationDto.builder().name(NAME).build();
		
		organization = Organization.builder()
				.id(ID)
				.name(NAME)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.practices(Stream.of(
							Practice.builder().id(1L).build(),
							Practice.builder().id(2L).build()
							).collect(Collectors.toSet()))
				.build();
	}

	@Test
	void testCreateOrganization() {
		when(organizationRepository.save(any())).then(returnsFirstArg());
		
		OrganizationDto savedOrganization = organizationService.createOrganization(organizationDto);
		
		verify(organizationRepository, times(1)).save(any());
		assertEquals(NAME, savedOrganization.getName());
	}

	@Test
	void testGetOrganizations() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Organization> organizations = ids.stream()
				.map(id -> Organization.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L).build(),
							Practice.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(organizationRepository.findAllById(ids)).thenReturn(organizations);
		
		Set<OrganizationDto> organizationDtos = organizationService.getOrganizations(ids, false);
		
		verify(organizationRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), organizationDtos.size());
		assertEquals(0, organizationDtos.iterator().next().getPractices().size());
	}
	
	@Test
	void testGetOrganizationsWithPractices() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Organization> organizations = ids.stream()
				.map(id -> Organization.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L).build(),
							Practice.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(organizationRepository.findAllById(ids)).thenReturn(organizations);
		
		Set<OrganizationDto> organizationDtos = organizationService.getOrganizations(ids, true);
		
		verify(organizationRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), organizationDtos.size());
		assertEquals(2, organizationDtos.iterator().next().getPractices().size());
	}

	@Test
	void testUpdateName() {
		String updatedName = "Updated Org Name";
		organizationDto.setName(updatedName);
		organizationDto.setId(ID);
		
		when(organizationRepository.findById(ID)).thenReturn(Optional.of(organization));
		when(organizationRepository.save(any())).then(returnsFirstArg());
		
		OrganizationDto updateOrganizationDto = organizationService.updateName(organizationDto);
		
		verify(organizationRepository, times(1)).findById(ID);
		verify(organizationRepository, times(1)).save(any());
		
		assertEquals(updatedName, updateOrganizationDto.getName());
	}

	@Test
	void testFindOrganizationsByName() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Organization> organizations = ids.stream()
				.map(id -> Organization.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L).build(),
							Practice.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(organizationRepository.findAllByName(NAME.toLowerCase())).thenReturn(organizations);
		
		Set<OrganizationDto> organizationDtos = organizationService.findOrganizationsByName(NAME, false);
		
		verify(organizationRepository, times(1)).findAllByName(NAME.toLowerCase());
		assertEquals(ids.size(), organizationDtos.size());
		assertEquals(0, organizationDtos.iterator().next().getPractices().size());
	}
	
	@Test
	void testFindOrganizationsByNameWitPractices() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Organization> organizations = ids.stream()
				.map(id -> Organization.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L).build(),
							Practice.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(organizationRepository.findAllByName(NAME.toLowerCase())).thenReturn(organizations);
		
		Set<OrganizationDto> organizationDtos = organizationService.findOrganizationsByName(NAME, true);
		
		verify(organizationRepository, times(1)).findAllByName(NAME.toLowerCase());
		assertEquals(ids.size(), organizationDtos.size());
		assertEquals(2, organizationDtos.iterator().next().getPractices().size());
	}

	@Test
	void testFindOrganizationById() {
		when(organizationRepository.findById(ID)).thenReturn(Optional.of(organization));
		
		OrganizationDto savedOrganizationDto = organizationService.findOrganizationById(ID, false);
		
		verify(organizationRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedOrganizationDto.getId());
		assertEquals(0, savedOrganizationDto.getPractices().size());
	}
	
	@Test
	void testFindOrganizationByIdWithPractices() {
		when(organizationRepository.findById(ID)).thenReturn(Optional.of(organization));
		
		OrganizationDto savedOrganizationDto = organizationService.findOrganizationById(ID, true);
		
		verify(organizationRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedOrganizationDto.getId());
		assertEquals(2, savedOrganizationDto.getPractices().size());
	}
	
	@Test
	public void testDeleteOrganization() {
		when(organizationRepository.findById(ID)).thenReturn(Optional.of(organization));
		doNothing().when(organizationRepository).delete(organization);
		
		OrganizationDto deletedOrganizationDto = organizationService.deleteOrganization(ID);
		
		assertEquals(organization.getId(), deletedOrganizationDto.getId());
		assertEquals(organization.getName(), deletedOrganizationDto.getName());
	}
	
	@Test
	void testCreatePractice() {
		PracticeDto practiceDto = PracticeDto.builder().name(NAME).build();
		
		when(organizationRepository.findById(ID)).thenReturn(Optional.of(organization));
		when(practiceService.createPractice(practiceDto, organization)).thenReturn(practiceDto);
		
		PracticeDto createdPractice = organizationService.createPractice(ID, practiceDto);
		
		assertEquals(NAME, createdPractice.getName());
	}

}
