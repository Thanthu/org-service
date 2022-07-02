package com.thanthu.orgservice.controllers;

import static com.thanthu.orgservice.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.enums.UserType;
import com.thanthu.orgservice.exceptions.NotFoundException;
import com.thanthu.orgservice.services.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
	
	private static final String API_BASE_URL = "/api/v1/user";
	private static final Long ID = 1L;
	private static final String FIRST_NAME = "Thanthu";
	private static final String LAST_NAME = "Nair";
	private static final UserType USER_TYPE = UserType.DOCTOR;
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private UserDto userDto;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@BeforeEach
	void setUp() throws Exception {
		userDto = UserDto.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.type(USER_TYPE)
				.build();
	}

	@Test
	void testCreateUser() throws Exception {
		UserDto createdUserDto = UserDto.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.type(USER_TYPE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
		
		when(userService.createUser(any(UserDto.class))).thenReturn(createdUserDto);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto))
				).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(ID))
				.andExpect(jsonPath("$.firstName").value(FIRST_NAME))
				.andExpect(jsonPath("$.lastName").value(LAST_NAME))
				.andExpect(jsonPath("$.type").value(USER_TYPE.toString()))
				.andExpect(jsonPath("$.createdDateTime").exists())
				.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(userService, times(1)).createUser(any());
	}
	
	@Test
	void testCreateUserFirstNameNull() throws Exception {
		userDto.setFirstName(null);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto))
				).andExpect(status().isBadRequest());
		
		verify(userService, times(0)).createUser(any());
	}
	
	@Test
	void testCreateUserLastNameNull() throws Exception {
		userDto.setLastName(null);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto))
				).andExpect(status().isBadRequest());
		
		verify(userService, times(0)).createUser(any());
	}
	
	@Test
	void testCreateUserTypeNull() throws Exception {
		userDto.setType(null);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto))
				).andExpect(status().isBadRequest());
		
		verify(userService, times(0)).createUser(any());
	}

	@Test
	void testGetUsers() throws Exception {
		Set<UserDto> set = new HashSet<>();
		set.add(UserDto.builder()
				.id(ID)
				.build());
		set.add(UserDto.builder()
				.id(2L)
				.build());
		
		when(userService.getUsers(anyList(), anyBoolean(), anyBoolean())).thenReturn(set);
		
		mockMvc.perform(get(API_BASE_URL + "/list-by-ids")
				.contentType(MediaType.APPLICATION_JSON)
				.param("ids", "1", "2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[:2].id").value(containsInAnyOrder(1, 2)));
		
		verify(userService, times(1)).getUsers(anyList(), anyBoolean(), anyBoolean());
	}

	@Test
	void testUpdateName() throws Exception {
		UserDto updatedUserDto = UserDto.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.type(USER_TYPE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
		
		when(userService.updateName(any())).thenReturn(updatedUserDto);
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID))
		.andExpect(jsonPath("$.firstName").value(FIRST_NAME))
		.andExpect(jsonPath("$.lastName").value(LAST_NAME))
		.andExpect(jsonPath("$.type").value(USER_TYPE.toString()))
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(userService, times(1)).updateName(any());
	}
	
	@Test
	void testUpdateNameNotFound() throws Exception {
		when(userService.updateName(any())).thenThrow(new NotFoundException(""));
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto)))
		.andExpect(status().isNotFound());
		
		verify(userService, times(1)).updateName(any());
	}
	
	@Test
	void testUpdateNameFirstNameNull() throws Exception {
		userDto.setFirstName(null);
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto)))
		.andExpect(status().isBadRequest());
		
		verify(userService, times(0)).updateName(any());
	}
	
	@Test
	void testUpdateNameLastNameNull() throws Exception {
		userDto.setLastName(null);
		
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(userDto)))
		.andExpect(status().isBadRequest());
		
		verify(userService, times(0)).updateName(any());
	}

	@Test
	void testFindUsersByName() throws Exception {
		Set<UserDto> set = new HashSet<>();
		set.add(UserDto.builder()
				.id(ID)
				.build());
		set.add(UserDto.builder()
				.id(2L)
				.build());
		
		when(userService.findUsersByName(anyString(), anyBoolean(), anyBoolean())).thenReturn(set);
		
		mockMvc.perform(get(API_BASE_URL + "/list-by-name")
				.contentType(MediaType.APPLICATION_JSON)
				.param("name", "Thanthu"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[:2].id").value(containsInAnyOrder(1, 2)));
		
		verify(userService, times(1)).findUsersByName(anyString(), anyBoolean(), anyBoolean());
	}

	@Test
	void testFindUserById() throws Exception {
		UserDto createdUserDto = UserDto.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.type(USER_TYPE)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
		
		when(userService.findUserById(ID, false, false)).thenReturn(createdUserDto);
		
		mockMvc.perform(get(API_BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(ID.toString()))
				.andExpect(jsonPath("$.firstName").value(FIRST_NAME))
				.andExpect(jsonPath("$.lastName").value(LAST_NAME))
				.andExpect(jsonPath("$.type").exists())
				.andExpect(jsonPath("$.createdDateTime").exists())
				.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(userService, times(1)).findUserById(ID, false, false);
	}
	
	@Test
	void testFindUserByIdNotFound() throws Exception {
		when(userService.findUserById(any(), anyBoolean(), anyBoolean())).thenThrow(new NotFoundException(""));
		
		mockMvc.perform(get(API_BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isNotFound());
		
		verify(userService, times(1)).findUserById(any(), anyBoolean(), anyBoolean());
	}

}
