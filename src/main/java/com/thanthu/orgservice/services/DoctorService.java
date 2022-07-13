package com.thanthu.orgservice.services;

import java.util.Set;

import com.thanthu.orgservice.dtos.PatientDto;

public interface DoctorService {

	public Set<PatientDto> listPatientsByDoctor(Long id);

}
