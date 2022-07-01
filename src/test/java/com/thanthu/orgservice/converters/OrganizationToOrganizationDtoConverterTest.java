package com.thanthu.orgservice.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.model.Practice;

class OrganizationToOrganizationDtoConverterTest {
	
	private static final Long ID = 1L;
	private static final String NAME = "Test Org Name";
	private static final LocalDateTime DATE_TIME = LocalDateTime.now();
	private Organization organization;
	
	private OrganizationToOrganizationDtoConverter organizationToOrganizationDtoConverter;

	@BeforeEach
	void setUp() throws Exception {
		organizationToOrganizationDtoConverter = new OrganizationToOrganizationDtoConverter();
		
		Set<Practice> practices = new HashSet<>();
		practices.add(Practice.builder().id(1L).build());
		practices.add(Practice.builder().id(2L).build());
		
		organization = Organization.builder()
				.id(ID)
				.name(NAME)
				.createdDateTime(DATE_TIME)
				.updateDateTime(DATE_TIME)
				.practices(practices)
				.build();
	}

	@Test
	void testConvert() {
		OrganizationDto organizationDto = organizationToOrganizationDtoConverter.convert(organization);
		
		assertEquals(ID, organizationDto.getId());
		assertEquals(NAME, organizationDto.getName());
		assertEquals(DATE_TIME, organizationDto.getCreatedDateTime());
		assertEquals(DATE_TIME, organizationDto.getUpdateDateTime());
		assertEquals(0, organizationDto.getPractices().size());
	}
	
	@Test
	void testConvertNull() {
		OrganizationDto organizationDto = organizationToOrganizationDtoConverter.convert(null);
		assertNull(organizationDto);
	}

	@Test
	void testConvertWithPractices() {
		OrganizationDto organizationDto = organizationToOrganizationDtoConverter.convertWithPractices(organization);
		
		assertEquals(ID, organizationDto.getId());
		assertEquals(NAME, organizationDto.getName());
		assertEquals(DATE_TIME, organizationDto.getCreatedDateTime());
		assertEquals(DATE_TIME, organizationDto.getUpdateDateTime());
		assertEquals(2, organizationDto.getPractices().size());
	}
	
	@Test
	void testConvertWithPracticesNull() {
		OrganizationDto organizationDto = organizationToOrganizationDtoConverter.convertWithPractices(null);
		assertNull(organizationDto);
	}

}
