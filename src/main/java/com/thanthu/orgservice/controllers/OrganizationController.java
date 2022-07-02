package com.thanthu.orgservice.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thanthu.orgservice.dtos.OrganizationDto;
import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.services.OrganizationService;
import com.thanthu.orgservice.validation.groups.OnCreateOrganization;
import com.thanthu.orgservice.validation.groups.OnCreatePractice;
import com.thanthu.orgservice.validation.groups.OnUpdateOrganizationName;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/organization")
@RestController
@Validated
public class OrganizationController {
	
	private final OrganizationService organizationService;

	@PostMapping
	@Validated(OnCreateOrganization.class)
	public OrganizationDto createOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
		return organizationService.createOrganization(organizationDto);
	}

	@GetMapping("/list-by-ids")
	public Set<OrganizationDto> getOrganizations(@RequestParam(required = true) List<Long> ids,
			@RequestParam(defaultValue = "false") boolean showPractices) {
		return organizationService.getOrganizations(ids, showPractices);
	}

	@PutMapping("/{id}/name")
	@Validated(OnUpdateOrganizationName.class)
	public OrganizationDto updateName(@Valid @RequestBody OrganizationDto organizationDto, @PathVariable Long id) {
		organizationDto.setId(id);
		return organizationService.updateName(organizationDto);
	}

	@GetMapping("/list-by-name")
	public Set<OrganizationDto> findOrganizationsByName(@RequestParam(required = true) String name,
			@RequestParam(defaultValue = "false") boolean showPractices) {
		return organizationService.findOrganizationsByName(name, showPractices);
	}

	@GetMapping("/{id}")
	public OrganizationDto findOrganizationById(@PathVariable Long id,
			@RequestParam(defaultValue = "false") boolean showPractices) {
		return organizationService.findOrganizationById(id, showPractices);
	}
	
	@PostMapping("/{id}/practice")
	@Validated(OnCreatePractice.class)
	public PracticeDto createPractice(@PathVariable Long id, @Valid @RequestBody PracticeDto practiceDto) {
		return organizationService.createPractice(id, practiceDto);
	}
	
	@DeleteMapping("/{id}")
	public OrganizationDto deleteOrganization(@PathVariable Long id) {
		return organizationService.deleteOrganization(id);
	}

}
