package com.thanthu.orgservice.services;

import java.util.List;
import java.util.Set;

import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.model.Practice;

public interface PracticeService {
	
	public PracticeDto createPractice(PracticeDto practiceDto, Organization organization);
	
	public Set<PracticeDto> getPractices(List<Long> ids, boolean showUsers, boolean showOrganization);

	public PracticeDto updateName(PracticeDto practiceDto);
	
	public Set<PracticeDto> findPracticesByName(String name, boolean showUsers, boolean showOrganization);

	public PracticeDto findPracticeById(Long id, boolean showUsers, boolean showOrganization);

	public Practice findById(Long id);

}
