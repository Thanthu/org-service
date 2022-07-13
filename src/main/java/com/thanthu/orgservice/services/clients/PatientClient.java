package com.thanthu.orgservice.services.clients;

import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.thanthu.orgservice.dtos.PatientDto;

@FeignClient("patient")
public interface PatientClient {
	
	@PostMapping(value = "/api/v1/patient", consumes = MediaType.APPLICATION_JSON_VALUE)
	public PatientDto createPatient(@RequestBody PatientDto patientDto);
	
	@GetMapping("/api/v1/patient/list-by-doctor")
	public Set<PatientDto> listPatientsByDoctor(@RequestParam Long doctorId);
	
	@GetMapping("/api/v1/patient/list-by-practice")
	public Set<PatientDto> listPatientsByPractice(@RequestParam Long practiceId);

}
