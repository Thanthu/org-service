package com.thanthu.orgservice.services;

import javax.validation.Valid;

import com.thanthu.orgservice.dtos.PatientDto;

public interface PatientService {

	public PatientDto createPatient(@Valid PatientDto patientDto);

}
