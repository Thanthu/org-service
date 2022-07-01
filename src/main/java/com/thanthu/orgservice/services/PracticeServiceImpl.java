package com.thanthu.orgservice.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thanthu.orgservice.converters.PracticeDtoToPracticeConverter;
import com.thanthu.orgservice.converters.PracticeToPracticeDtoConverter;
import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.exceptions.NotFoundException;
import com.thanthu.orgservice.model.Practice;
import com.thanthu.orgservice.repositories.PracticeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PracticeServiceImpl implements PracticeService {
	
	private final PracticeDtoToPracticeConverter practiceDtoToPracticeConverter;
	
	private final PracticeToPracticeDtoConverter practiceToPracticeDtoConverter;
	
	private final PracticeRepository practiceRepository;
	
	@Override
	public PracticeDto createPractice(PracticeDto practiceDto) {
		Practice practice = practiceDtoToPracticeConverter.convert(practiceDto);
		Practice savedPractice = savePractice(practice);
		return practiceToPracticeDtoConverter.convert(savedPractice);
	}

	@Override
	public Set<PracticeDto> getPractices(List<Long> ids, boolean showUsers, boolean showOrganization) {
		List<Practice> practices = practiceRepository.findAllById(ids);
		return convertPracticesToPracticeDtos(practices, showUsers, showOrganization);
	}

	@Override
	public PracticeDto updateName(PracticeDto practiceDto) {
		Practice practice = findById(practiceDto.getId());
		practice.setName(practiceDto.getName());
		Practice savedPractice = savePractice(practice);
		return practiceToPracticeDtoConverter.convert(savedPractice);
	}

	@Override
	public Set<PracticeDto> findPracticesByName(String name, boolean showUsers, boolean showOrganization) {
		List<Practice> practices = practiceRepository.findAllByName(name.toLowerCase().trim());
		return convertPracticesToPracticeDtos(practices, showUsers, showOrganization);
	}

	@Override
	public PracticeDto findPracticeById(Long id, boolean showUsers, boolean showOrganization) {
		Practice practice = findById(id);
		if(showUsers && showOrganization) {
			return practiceToPracticeDtoConverter.convertWithUsersAndOrganization(practice);
		}
		
		if(showUsers) {
			return practiceToPracticeDtoConverter.convertWithUsers(practice);
		}
		
		if(showOrganization) {
			return practiceToPracticeDtoConverter.convertWithOrganization(practice);
		}
		
		return practiceToPracticeDtoConverter.convert(practice);
	}
	
	private Practice savePractice(Practice practice) {
		return practiceRepository.save(practice);
	}
	
	@Override
	public Practice findById(Long id) {
		return practiceRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Practice not found."));
	}
	
	private Set<PracticeDto> convertPracticesToPracticeDtos(List<Practice> practices, boolean showUsers, boolean showOrganization) {
		if(showUsers && showOrganization) {
			return practices.stream()
					.map(practice -> practiceToPracticeDtoConverter.convertWithUsersAndOrganization(practice))
					.collect(Collectors.toSet());
		}
		
		if(showUsers) {
			return practices.stream()
					.map(practice -> practiceToPracticeDtoConverter.convertWithUsers(practice))
					.collect(Collectors.toSet());
		}
		
		if(showOrganization) {
			return practices.stream()
					.map(practice -> practiceToPracticeDtoConverter.convertWithOrganization(practice))
					.collect(Collectors.toSet());
		}
		
		return practices.stream()
				.map(practice -> practiceToPracticeDtoConverter.convert(practice))
				.collect(Collectors.toSet());
	}

}
