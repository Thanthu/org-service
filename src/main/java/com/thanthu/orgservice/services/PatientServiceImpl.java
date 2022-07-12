package com.thanthu.orgservice.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.thanthu.orgservice.dtos.PatientDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "services.patient", ignoreUnknownFields = false)
public class PatientServiceImpl implements PatientService {

	private final RestTemplate restTemplate;

	private String baseUrl;

	private String createPatientUrl;
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setCreatePatientUrl(String createPatientUrl) {
		this.createPatientUrl = createPatientUrl;
	}

	@Override
	public PatientDto createPatient(@Valid PatientDto patientDto) {
		ResponseEntity<PatientDto> response = restTemplate.postForEntity(baseUrl + createPatientUrl, patientDto,
				PatientDto.class);
		return response.getBody();
	}

}
