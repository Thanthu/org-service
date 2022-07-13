package com.thanthu.orgservice.services;

import org.springframework.stereotype.Service;

import com.thanthu.orgservice.dtos.PatientDto;
import com.thanthu.orgservice.services.clients.PatientClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

	private final PatientClient patientClient; 
	
	@Override
	public PatientDto createPatient(PatientDto patientDto) {
		return patientClient.createPatient(patientDto);
	}

}
