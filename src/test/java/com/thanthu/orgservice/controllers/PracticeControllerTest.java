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

import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.exceptions.NotFoundException;
import com.thanthu.orgservice.services.PracticeService;

@WebMvcTest(PracticeController.class)
class PracticeControllerTest {

	private static final String API_BASE_URL = "/api/v1/practice";
	private static final Long ID = 1L;
	private static final String NAME = "Test Practice Name";
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private PracticeDto practiceDto;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PracticeService practiceService;

	@BeforeEach
	void setUp() throws Exception {
		practiceDto = PracticeDto.builder().name(NAME).build();
	}

	@Test
	void testCreatePractice() throws Exception {
		PracticeDto createdPracticeDto = PracticeDto.builder()
		.id(ID)
		.name(NAME)
		.createdDateTime(DATE_TIME)
		.updateDateTime(DATE_TIME)
		.build();
		
		when(practiceService.createPractice(any())).thenReturn(createdPracticeDto);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(practiceDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID))
		.andExpect(jsonPath("$.name").value(NAME))
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(practiceService, times(1)).createPractice(any());
	}
	
	@Test
	void testCreatePracticeNameNull() throws Exception {
		practiceDto.setName(null);
		
		mockMvc.perform(post(API_BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(practiceDto)))
		.andExpect(status().isBadRequest());
		
		verify(practiceService, times(0)).createPractice(any());
	}

	@Test
	void testGetPractices() throws Exception {
		Set<PracticeDto> set = new HashSet<>();
		set.add(PracticeDto.builder()
				.id(ID)
				.build());
		set.add(PracticeDto.builder()
				.id(2L)
				.build());
		
		when(practiceService.getPractices(anyList(), anyBoolean(), anyBoolean())).thenReturn(set);
		
		mockMvc.perform(get(API_BASE_URL + "/list-by-ids")
				.contentType(MediaType.APPLICATION_JSON)
				.param("ids", "1", "2"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[:2].id").value(containsInAnyOrder(1, 2)));
		
		verify(practiceService, times(1)).getPractices(anyList(), anyBoolean(), anyBoolean());
	}
	
	@Test
	void testUpdateName() throws Exception {
		PracticeDto updatedPracticeDto = PracticeDto.builder()
				.id(ID)
				.name(NAME)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
				
		when(practiceService.updateName(any())).thenReturn(updatedPracticeDto);
				
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(practiceDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID))
		.andExpect(jsonPath("$.name").value(NAME))
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(practiceService, times(1)).updateName(any());
	}
	
	@Test
	void testUpdateNameNotFound() throws Exception {
		when(practiceService.updateName(any())).thenThrow(new NotFoundException(""));
				
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(practiceDto)))
		.andExpect(status().isNotFound());
		
		verify(practiceService, times(1)).updateName(any());
	}
	
	@Test
	void testUpdateNameNull() throws Exception {
		practiceDto.setName(null);
				
		mockMvc.perform(put(API_BASE_URL + "/" + ID + "/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(practiceDto)))
		.andExpect(status().isBadRequest());
		
		verify(practiceService, times(0)).updateName(any());
	}

	@Test
	void testFindPracticesByName() throws Exception {
		Set<PracticeDto> set = new HashSet<>();
		set.add(PracticeDto.builder()
				.id(ID)
				.build());
		set.add(PracticeDto.builder()
				.id(2L)
				.build());
		
		when(practiceService.findPracticesByName(NAME, false, false)).thenReturn(set);
		
		mockMvc.perform(get(API_BASE_URL + "/list-by-name")
				.contentType(MediaType.APPLICATION_JSON)
				.param("name", NAME))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[:2].id").value(containsInAnyOrder(1, 2)));
		
		verify(practiceService, times(1)).findPracticesByName(NAME, false, false);
	}

	@Test
	void testFindPracticeById() throws Exception {
		PracticeDto savedPracticeDto = PracticeDto.builder()
				.id(ID)
				.name(NAME)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.build();
				
		when(practiceService.findPracticeById(ID, false, false)).thenReturn(savedPracticeDto);
		
		mockMvc.perform(get(API_BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID))
		.andExpect(jsonPath("$.name").value(NAME))
		.andExpect(jsonPath("$.createdDateTime").exists())
		.andExpect(jsonPath("$.updateDateTime").exists());
		
		verify(practiceService, times(1)).findPracticeById(ID, false, false);
	}
	
	@Test
	void testFindPracticeByIdNotFound() throws Exception {
		when(practiceService.findPracticeById(anyLong(), anyBoolean(), anyBoolean())).thenThrow(new NotFoundException(""));
		
		mockMvc.perform(get(API_BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
		
		verify(practiceService, times(1)).findPracticeById(anyLong(), anyBoolean(), anyBoolean());
	}

}
