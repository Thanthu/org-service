package com.thanthu.orgservice.controllers;

import static com.thanthu.orgservice.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.exceptions.NotFoundException;
import com.thanthu.orgservice.services.OrganizationService;

@WebMvcTest(OrganizationController.class)
class OrganizationControllerTest {

	private static final String API_BASE_URL = "/api/v1/organization";
	private static final Long ID = 1L;
	private static final String NAME = "Test Org Name";
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private OrganizationDto organizationDto;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrganizationService organizationService;

	@BeforeEach
	void setUp() throws Exception {
		organizationDto = OrganizationDto.builder().name(NAME).build();
	}

	@Test
	void testCreateOrganization() throws Exception {
		OrganizationDto createdOrganizationDto = OrganizationDto.builder()
		.id(ID)
		.name(NAME)
		.createdDateTime(DATE_TIME)
		.updateDateTime(DATE_TIME)
		.build();
		
		when(organizationService.createOrganization(any())).thenReturn(createdOrganizationDto);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(organizationDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID))
		.andExpect(jsonPath("$.name").value(NAME))
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(organizationService, times(1)).createOrganization(any());
	}
	
	@Test
	void testCreateOrganizationNameNull() throws Exception {
		organizationDto.setName(null);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(organizationDto)))
		.andExpect(status().isBadRequest());
		
		verify(organizationService, times(0)).createOrganization(any());
	}

	@Test
	void testGetOrganizations() throws Exception {
		Set<OrganizationDto> set = new HashSet<>();
		set.add(OrganizationDto.builder()
				.id(ID)
				.build());
		set.add(OrganizationDto.builder()
				.id(2L)
				.build());
		
		when(organizationService.getOrganizations(anyList(), anyBoolean())).thenReturn(set);
		
		mockMvc.perform(get(API_BASE_URL + "/list-by-ids")
				.contentType(MediaType.APPLICATION_JSON)
				.param("ids", "1", "2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[:2].id").value(containsInAnyOrder(1, 2)));
		
		verify(organizationService, times(1)).getOrganizations(anyList(), anyBoolean());
	}
	
	@Test
	void testUpdateName() throws Exception {
		OrganizationDto updatedOrganizationDto = OrganizationDto.builder()
				.id(ID)
				.name(NAME)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
				
		when(organizationService.updateName(any())).thenReturn(updatedOrganizationDto);
				
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(organizationDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID))
		.andExpect(jsonPath("$.name").value(NAME))
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(organizationService, times(1)).updateName(any());
	}
	
	@Test
	void testUpdateNameNotFound() throws Exception {
		when(organizationService.updateName(any())).thenThrow(new NotFoundException(""));
				
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(organizationDto)))
		.andExpect(status().isNotFound());
		
		verify(organizationService, times(1)).updateName(any());
	}
	
	@Test
	void testUpdateNameNull() throws Exception {
		organizationDto.setName(null);
				
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(organizationDto)))
		.andExpect(status().isBadRequest());
		
		verify(organizationService, times(0)).updateName(any());
	}

	@Test
	void testFindOrganizationsByName() throws Exception {
		Set<OrganizationDto> set = new HashSet<>();
		set.add(OrganizationDto.builder()
				.id(ID)
				.build());
		set.add(OrganizationDto.builder()
				.id(2L)
				.build());
		
		when(organizationService.findOrganizationsByName(NAME, false)).thenReturn(set);
		
		mockMvc.perform(get(API_BASE_URL + "/list-by-name")
				.contentType(MediaType.APPLICATION_JSON)
				.param("name", NAME))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[:2].id").value(containsInAnyOrder(1, 2)));
		
		verify(organizationService, times(1)).findOrganizationsByName(NAME, false);
	}

	@Test
	void testFindOrganizationById() throws Exception {
		OrganizationDto savedOrganizationDto = OrganizationDto.builder()
				.id(ID)
				.name(NAME)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
				
		when(organizationService.findOrganizationById(ID, false)).thenReturn(savedOrganizationDto);
		
		mockMvc.perform(get(API_BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID))
		.andExpect(jsonPath("$.name").value(NAME))
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(organizationService, times(1)).findOrganizationById(ID, false);
	}
	
	@Test
	void testFindOrganizationByIdNotFound() throws Exception {
		when(organizationService.findOrganizationById(anyLong(), anyBoolean())).thenThrow(new NotFoundException(""));
		
		mockMvc.perform(get(API_BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
		
		verify(organizationService, times(1)).findOrganizationById(anyLong(), anyBoolean());
	}

}
