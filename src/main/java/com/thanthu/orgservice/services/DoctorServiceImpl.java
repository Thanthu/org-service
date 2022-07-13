package com.thanthu.orgservice.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.thanthu.orgservice.dtos.PatientDto;
import com.thanthu.orgservice.services.clients.PatientClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService {

	private final PatientClient patientClient;

	@Override
	public Set<PatientDto> listPatientsByDoctor(Long doctorId) {
		return patientClient.listPatientsByDoctor(doctorId);
	}

}
