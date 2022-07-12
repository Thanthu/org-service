package com.thanthu.orgservice.controllers;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanthu.orgservice.dtos.PatientDto;
import com.thanthu.orgservice.services.PatientService;
import com.thanthu.orgservice.validation.groups.OnCreatePatient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/patient")
@Validated
public class PatientController {
	
	private final PatientService patientService;
	
	@PostMapping
	@Validated(OnCreatePatient.class)
	public PatientDto createPatient(@Valid @RequestBody PatientDto patientDto) {
		return patientService.createPatient(patientDto);
	}

}
