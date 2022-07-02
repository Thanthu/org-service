package com.thanthu.orgservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
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

import com.thanthu.orgservice.converters.PracticeDtoToPracticeConverter;
import com.thanthu.orgservice.converters.PracticeToPracticeDtoConverter;
import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.model.Practice;
import com.thanthu.orgservice.model.User;
import com.thanthu.orgservice.repositories.PracticeRepository;

@ExtendWith(MockitoExtension.class)
class PracticeServiceImplTest {
	
	private static final Long ID = 1L;
	private static final String NAME = "Test Practice Name";
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	
	private PracticeDto practiceDto;
	private Practice practice;
	
	@Spy
	private PracticeDtoToPracticeConverter practiceDtoToPracticeConverter;
	
	@Spy
	private PracticeToPracticeDtoConverter practiceToPracticeDtoConverter;
	
	@Mock
	private PracticeRepository practiceRepository;
	
	@InjectMocks
	private PracticeServiceImpl practiceService;

	@BeforeEach
	void setUp() throws Exception {
		practiceDto = PracticeDto.builder().name(NAME).build();
		
		practice = Practice.builder()
				.id(ID)
				.name(NAME)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
				.organization(Organization.builder().id(1L).build())
				.build();
	}

	@Test
	void testCreatePractice() {
		Organization organization = Organization.builder().id(ID).build();
		
		when(practiceRepository.save(any())).then(returnsFirstArg());
		
		PracticeDto savedPractice = practiceService.createPractice(practiceDto, organization);
		
		verify(practiceRepository, times(1)).save(any());
		assertEquals(NAME, savedPractice.getName());
	}

	@Test
	void testGetPractices() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Practice> practices = ids.stream()
				.map(id -> Practice.builder()
					.id(id)
					.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.organization(Organization.builder().id(1L).build())
					.build())
				.collect(Collectors.toList());
		
		when(practiceRepository.findAllById(ids)).thenReturn(practices);
		
		Set<PracticeDto> practiceDtos = practiceService.getPractices(ids, false, false);
		
		verify(practiceRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), practiceDtos.size());
		assertEquals(0, practiceDtos.iterator().next().getUsers().size());
		assertNull(practiceDtos.iterator().next().getOrganization());
	}
	
	@Test
	void testGetPracticesWithUsers() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Practice> practices = ids.stream()
				.map(id -> Practice.builder()
					.id(id)
					.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.organization(Organization.builder().id(1L).build())
					.build())
				.collect(Collectors.toList());
		
		when(practiceRepository.findAllById(ids)).thenReturn(practices);
		
		Set<PracticeDto> practiceDtos = practiceService.getPractices(ids, true, false);
		
		verify(practiceRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), practiceDtos.size());
		assertEquals(2, practiceDtos.iterator().next().getUsers().size());
		assertNull(practiceDtos.iterator().next().getOrganization());
	}
	
	@Test
	void testGetPracticesWithOrganization() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Practice> practices = ids.stream()
				.map(id -> Practice.builder()
					.id(id)
					.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.organization(Organization.builder().id(1L).build())
					.build())
				.collect(Collectors.toList());
		
		when(practiceRepository.findAllById(ids)).thenReturn(practices);
		
		Set<PracticeDto> practiceDtos = practiceService.getPractices(ids, false, true);
		
		verify(practiceRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), practiceDtos.size());
		assertEquals(0, practiceDtos.iterator().next().getUsers().size());
		assertNotNull(practiceDtos.iterator().next().getOrganization());
	}
	
	@Test
	void testGetPracticesWithUsersAndOrganization() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Practice> practices = ids.stream()
				.map(id -> Practice.builder()
					.id(id)
					.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.organization(Organization.builder().id(1L).build())
					.build())
				.collect(Collectors.toList());
		
		when(practiceRepository.findAllById(ids)).thenReturn(practices);
		
		Set<PracticeDto> practiceDtos = practiceService.getPractices(ids, true, true);
		
		verify(practiceRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), practiceDtos.size());
		assertEquals(2, practiceDtos.iterator().next().getUsers().size());
		assertNotNull(practiceDtos.iterator().next().getOrganization());
	}

	@Test
	void testUpdateName() {
		String updatedName = "Updated Practice Name";
		practiceDto.setName(updatedName);
		practiceDto.setId(ID);
		
		when(practiceRepository.findById(ID)).thenReturn(Optional.of(practice));
		when(practiceRepository.save(any())).then(returnsFirstArg());
		
		PracticeDto updatePracticeDto = practiceService.updateName(practiceDto);
		
		verify(practiceRepository, times(1)).findById(ID);
		verify(practiceRepository, times(1)).save(any());
		
		assertEquals(updatedName, updatePracticeDto.getName());
	}

	@Test
	void testFindPracticesByName() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Practice> practices = ids.stream()
				.map(id -> Practice.builder()
					.id(id)
					.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.organization(Organization.builder().id(1L).build())
					.build())
				.collect(Collectors.toList());
		
		when(practiceRepository.findAllByName(NAME.toLowerCase())).thenReturn(practices);
		
		Set<PracticeDto> practiceDtos = practiceService.findPracticesByName(NAME, false, false);
		
		verify(practiceRepository, times(1)).findAllByName(NAME.toLowerCase());
		assertEquals(ids.size(), practiceDtos.size());
		assertEquals(0, practiceDtos.iterator().next().getUsers().size());
		assertNull(practiceDtos.iterator().next().getOrganization());
	}
	
	@Test
	void testFindPracticesByNameWithUsers() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Practice> practices = ids.stream()
				.map(id -> Practice.builder()
					.id(id)
					.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.organization(Organization.builder().id(1L).build())
					.build())
				.collect(Collectors.toList());
		
		when(practiceRepository.findAllByName(NAME.toLowerCase())).thenReturn(practices);
		
		Set<PracticeDto> practiceDtos = practiceService.findPracticesByName(NAME, true, false);
		
		verify(practiceRepository, times(1)).findAllByName(NAME.toLowerCase());
		assertEquals(ids.size(), practiceDtos.size());
		assertEquals(2, practiceDtos.iterator().next().getUsers().size());
		assertNull(practiceDtos.iterator().next().getOrganization());
	}
	
	@Test
	void testFindPracticesByNameWithOrganization() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Practice> practices = ids.stream()
				.map(id -> Practice.builder()
					.id(id)
					.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.organization(Organization.builder().id(1L).build())
					.build())
				.collect(Collectors.toList());
		
		when(practiceRepository.findAllByName(NAME.toLowerCase())).thenReturn(practices);
		
		Set<PracticeDto> practiceDtos = practiceService.findPracticesByName(NAME, false, true);
		
		verify(practiceRepository, times(1)).findAllByName(NAME.toLowerCase());
		assertEquals(ids.size(), practiceDtos.size());
		assertEquals(0, practiceDtos.iterator().next().getUsers().size());
		assertNotNull(practiceDtos.iterator().next().getOrganization());
	}
	
	@Test
	void testFindPracticesByNameWithUsersAndOrganization() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<Practice> practices = ids.stream()
				.map(id -> Practice.builder()
					.id(id)
					.users(Stream.of(
							User.builder().id(1L).build(),
							User.builder().id(2L).build()
							).collect(Collectors.toSet()))
					.organization(Organization.builder().id(1L).build())
					.build())
				.collect(Collectors.toList());
		
		when(practiceRepository.findAllByName(NAME)).thenReturn(practices);
		
		Set<PracticeDto> practiceDtos = practiceService.findPracticesByName(NAME, true, true);
		
		verify(practiceRepository, times(1)).findAllByName(NAME);
		assertEquals(ids.size(), practiceDtos.size());
		assertEquals(2, practiceDtos.iterator().next().getUsers().size());
		assertNotNull(practiceDtos.iterator().next().getOrganization());
	}

	@Test
	void testFindPracticeById() {
		when(practiceRepository.findById(ID)).thenReturn(Optional.of(practice));
		
		PracticeDto savedPracticeDto = practiceService.findPracticeById(ID, false, false);
		
		verify(practiceRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedPracticeDto.getId());
		assertEquals(0, savedPracticeDto.getUsers().size());
		assertNull(savedPracticeDto.getOrganization());
	}
	
	@Test
	void testFindPracticeByIdWithUsers() {
		when(practiceRepository.findById(ID)).thenReturn(Optional.of(practice));
		
		PracticeDto savedPracticeDto = practiceService.findPracticeById(ID, true, false);
		
		verify(practiceRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedPracticeDto.getId());
		assertEquals(2, savedPracticeDto.getUsers().size());
		assertNull(savedPracticeDto.getOrganization());
	}
	
	
	@Test
	void testFindPracticeByIdWithOrganization() {
		when(practiceRepository.findById(ID)).thenReturn(Optional.of(practice));
		
		PracticeDto savedPracticeDto = practiceService.findPracticeById(ID, false, true);
		
		verify(practiceRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedPracticeDto.getId());
		assertEquals(0, savedPracticeDto.getUsers().size());
		assertNotNull(savedPracticeDto.getOrganization());
	}
	
	
	@Test
	void testFindPracticeByIdWithUsersAndOrganization() {
		when(practiceRepository.findById(ID)).thenReturn(Optional.of(practice));
		
		PracticeDto savedPracticeDto = practiceService.findPracticeById(ID, true, true);
		
		verify(practiceRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedPracticeDto.getId());
		assertEquals(2, savedPracticeDto.getUsers().size());
		assertNotNull(savedPracticeDto.getOrganization());
	}

}
