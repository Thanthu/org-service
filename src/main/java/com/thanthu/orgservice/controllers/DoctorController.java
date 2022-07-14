package com.thanthu.orgservice.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanthu.orgservice.dtos.PatientDto;
import com.thanthu.orgservice.services.DoctorService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
	
	private final DoctorService doctorService;
	
	@CircuitBreaker(name = "doctorControllerListPatientsByDoctor")
	@GetMapping("/{id}/patients")
	public Set<PatientDto> listPatientsByDoctor(@PathVariable Long id) {
		return doctorService.listPatientsByDoctor(id);
	}

}
