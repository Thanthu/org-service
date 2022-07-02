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

import com.thanthu.orgservice.converters.UserDtoToUserConverter;
import com.thanthu.orgservice.converters.UserToUserDtoConverter;
import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.enums.UserType;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.model.Practice;
import com.thanthu.orgservice.model.User;
import com.thanthu.orgservice.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	private static final Long ID = 1L;
	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final UserType USER_TYPE = UserType.DOCTOR;
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	
	private UserDto userDto;
	private User user;
	
	@Spy
	private UserDtoToUserConverter userDtoToUserConverter;
	
	@Spy
	private UserToUserDtoConverter userToUserDtoConverter;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() throws Exception {
		userDto = UserDto.builder().firstName(FIRST_NAME).lastName(LAST_NAME).type(USER_TYPE).build();
		
		user = User.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.type(USER_TYPE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.practices(Stream.of(
							Practice.builder().id(1L)
							.organization(
									Organization.builder().id(1L).build())
							.build(),
							Practice.builder().id(2L)
							.organization(
									Organization.builder().id(1L).build())
							.build()
							).collect(Collectors.toSet()))
				.build();
	}

	@Test
	void testCreateUser() {
		when(userRepository.save(any())).then(returnsFirstArg());
		
		UserDto savedUser = userService.createUser(userDto);
		
		verify(userRepository, times(1)).save(any());
		assertEquals(FIRST_NAME, savedUser.getFirstName());
		assertEquals(LAST_NAME, savedUser.getLastName());
		assertEquals(USER_TYPE, savedUser.getType());
	}

	@Test
	void testGetUsers() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<User> users = ids.stream()
				.map(id -> User.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L)
							.organization(
									Organization.builder().id(1L).build())
							.build(),
							Practice.builder().id(2L)
							.organization(
									Organization.builder().id(1L).build())
							.build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(userRepository.findAllById(ids)).thenReturn(users);
		
		Set<UserDto> userDtos = userService.getUsers(ids, false, false);
		
		verify(userRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), userDtos.size());
		assertEquals(0, userDtos.iterator().next().getPractices().size());
	}
	
	@Test
	void testGetUsersWithPractices() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<User> users = ids.stream()
				.map(id -> User.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L)
							.organization(
									Organization.builder().id(1L).build())
							.build(),
							Practice.builder().id(2L)
							.organization(
									Organization.builder().id(1L).build())
							.build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(userRepository.findAllById(ids)).thenReturn(users);
		
		Set<UserDto> userDtos = userService.getUsers(ids, true, false);
		
		verify(userRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), userDtos.size());
		assertEquals(2, userDtos.iterator().next().getPractices().size());
		assertNull(userDtos.iterator().next().getPractices().iterator().next().getOrganization());
	}
	
	@Test
	void testGetUsersWithOrganization() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<User> users = ids.stream()
				.map(id -> User.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L)
							.organization(
									Organization.builder().id(1L).build())
							.build(),
							Practice.builder().id(2L)
							.organization(
									Organization.builder().id(1L).build())
							.build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(userRepository.findAllById(ids)).thenReturn(users);
		
		Set<UserDto> userDtos = userService.getUsers(ids, true, true);
		
		verify(userRepository, times(1)).findAllById(ids);
		assertEquals(ids.size(), userDtos.size());
		assertEquals(2, userDtos.iterator().next().getPractices().size());
		assertNotNull(userDtos.iterator().next().getPractices().iterator().next().getOrganization());
	}

	@Test
	void testUpdateName() {
		String updatedFirstName = "John";
		String updatedLastName = "Doe";
		userDto.setFirstName(updatedFirstName);
		userDto.setLastName(updatedLastName);
		userDto.setId(ID);
		
		when(userRepository.findById(ID)).thenReturn(Optional.of(user));
		when(userRepository.save(any())).then(returnsFirstArg());
		
		UserDto updateUserDto = userService.updateName(userDto);
		
		verify(userRepository, times(1)).findById(ID);
		verify(userRepository, times(1)).save(any());
		
		assertEquals(updatedFirstName, updateUserDto.getFirstName());
		assertEquals(updatedLastName, updateUserDto.getLastName());
		assertEquals(USER_TYPE, updateUserDto.getType());
	}

	@Test
	void testFindUsersByName() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<User> users = ids.stream()
				.map(id -> User.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L)
							.organization(
									Organization.builder().id(1L).build())
							.build(),
							Practice.builder().id(2L)
							.organization(
									Organization.builder().id(1L).build())
							.build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(userRepository.findAllByName(FIRST_NAME.toLowerCase())).thenReturn(users);
		
		Set<UserDto> userDtos = userService.findUsersByName(FIRST_NAME, false, false);
		
		verify(userRepository, times(1)).findAllByName(FIRST_NAME.toLowerCase());
		assertEquals(ids.size(), userDtos.size());
		assertEquals(0, userDtos.iterator().next().getPractices().size());
	}
	
	@Test
	void testFindUsersByNameWithPractices() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<User> users = ids.stream()
				.map(id -> User.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L)
							.organization(
									Organization.builder().id(1L).build())
							.build(),
							Practice.builder().id(2L)
							.organization(
									Organization.builder().id(1L).build())
							.build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(userRepository.findAllByName(FIRST_NAME.toLowerCase())).thenReturn(users);
		
		Set<UserDto> userDtos = userService.findUsersByName(FIRST_NAME, true, false);
		
		verify(userRepository, times(1)).findAllByName(FIRST_NAME.toLowerCase());
		assertEquals(ids.size(), userDtos.size());
		assertEquals(2, userDtos.iterator().next().getPractices().size());
		assertNull(userDtos.iterator().next().getPractices().iterator().next().getOrganization());
	}
	
	@Test
	void testFindUsersByNameWithOrganization() {
		List<Long> ids = Arrays.asList(1L, 2L);
		List<User> users = ids.stream()
				.map(id -> User.builder()
					.id(id)
					.practices(Stream.of(
							Practice.builder().id(1L)
							.organization(
									Organization.builder().id(1L).build())
							.build(),
							Practice.builder().id(2L)
							.organization(
									Organization.builder().id(1L).build())
							.build()
							).collect(Collectors.toSet()))
					.build())
				.collect(Collectors.toList());
		
		when(userRepository.findAllByName(FIRST_NAME.toLowerCase())).thenReturn(users);
		
		Set<UserDto> userDtos = userService.findUsersByName(FIRST_NAME, true, true);
		
		verify(userRepository, times(1)).findAllByName(FIRST_NAME.toLowerCase());
		assertEquals(ids.size(), userDtos.size());
		assertEquals(2, userDtos.iterator().next().getPractices().size());
		assertNotNull(userDtos.iterator().next().getPractices().iterator().next().getOrganization());
	}

	@Test
	void testFindUserById() {
		when(userRepository.findById(ID)).thenReturn(Optional.of(user));
		
		UserDto savedUserDto = userService.findUserById(ID, false, false);
		
		verify(userRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedUserDto.getId());
		assertEquals(0, savedUserDto.getPractices().size());
	}
	
	@Test
	void testFindUserByIdWithPractices() {
		when(userRepository.findById(ID)).thenReturn(Optional.of(user));
		
		UserDto savedUserDto = userService.findUserById(ID, true, false);
		
		verify(userRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedUserDto.getId());
		assertEquals(2, savedUserDto.getPractices().size());
		assertNull(savedUserDto.getPractices().iterator().next().getOrganization());
	}
	
	@Test
	void testFindUserByIdWithOrganization() {
		when(userRepository.findById(ID)).thenReturn(Optional.of(user));
		
		UserDto savedUserDto = userService.findUserById(ID, true, true);
		
		verify(userRepository, times(1)).findById(ID);
		
		assertEquals(ID, savedUserDto.getId());
		assertEquals(2, savedUserDto.getPractices().size());
		assertNotNull(savedUserDto.getPractices().iterator().next().getOrganization());
	}

}
