package com.thanthu.orgservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.thanthu.orgservice.dtos.PracticeDto;
import com.thanthu.orgservice.model.Practice;

@Component
public class PracticeDtoToPracticeConverter implements Converter<PracticeDto, Practice> {
	
	@Override
	public Practice convert(PracticeDto practiceDto) {
		if (practiceDto == null) return null;
		
		return Practice.builder()
				.name(practiceDto.getName())
				.build();
	}
	
}
