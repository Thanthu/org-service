package com.thanthu.orgservice.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.model.Organization;

@ExtendWith(MockitoExtension.class)
class OrganizationDtoToOrganizationConverterTest {

	private static final String NAME = "Test Org Name";

	private OrganizationDtoToOrganizationConverter converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new OrganizationDtoToOrganizationConverter();
	}

	@Test
	void testConvert() {
		OrganizationDto organizationDto = OrganizationDto.builder().name(NAME).build();
		Organization organization = converter.convert(organizationDto);
		
		assertNull(organization.getId());
		assertEquals(NAME, organization.getName());
	}
	
	@Test
	void testConvertNull() {
		Organization organization = converter.convert(null);
		assertNull(organization);
	}

}
