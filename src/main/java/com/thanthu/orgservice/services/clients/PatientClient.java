package com.thanthu.orgservice.services.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thanthu.orgservice.dtos.PatientDto;

@FeignClient("patient")
public interface PatientClient {
	
	@PostMapping(value = "/api/v1/patient", consumes = MediaType.APPLICATION_JSON_VALUE)
	public PatientDto createPatient(@RequestBody PatientDto patientDto);

}
