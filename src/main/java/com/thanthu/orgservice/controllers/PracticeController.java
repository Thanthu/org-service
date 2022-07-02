package com.thanthu.orgservice.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.services.PracticeService;
import com.thanthu.orgservice.validation.groups.OnUpdatePracticeName;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/practice")
@RestController
@Validated
public class PracticeController {
	
	private final PracticeService practiceService;

	@GetMapping("/list-by-ids")
	public Set<PracticeDto> getPractices(@RequestParam(required = true) List<Long> ids,
			@RequestParam(defaultValue = "false") boolean showUsers,
			@RequestParam(defaultValue = "false") boolean showOrganization) {
		return practiceService.getPractices(ids, showUsers, showOrganization);
	}

	@PutMapping("/{id}/name")
	@Validated(OnUpdatePracticeName.class)
	public PracticeDto updateName(@Valid @RequestBody PracticeDto practiceDto, @PathVariable Long id) {
		practiceDto.setId(id);
		return practiceService.updateName(practiceDto);
	}

	@GetMapping("/list-by-name")
	public Set<PracticeDto> findPracticesByName(@RequestParam(required = true) String name,
			@RequestParam(defaultValue = "false") boolean showUsers,
			@RequestParam(defaultValue = "false") boolean showOrganization) {
		return practiceService.findPracticesByName(name, showUsers, showOrganization);
	}

	@GetMapping("/{id}")
	public PracticeDto findPracticeById(@PathVariable Long id,
			@RequestParam(defaultValue = "false") boolean showUsers,
			@RequestParam(defaultValue = "false") boolean showOrganization) {
		return practiceService.findPracticeById(id, showUsers, showOrganization);
	}
	
	@PostMapping("/{id}/user/{userId}")
	@Validated(OnUpdatePracticeName.class)
	public void addUserToPractice(@PathVariable Long id, @PathVariable Long userId) {
		practiceService.addUserToPractice(id, userId);
	}

}
