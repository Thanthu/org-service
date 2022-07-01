package com.thanthu.orgservice.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.thanthu.orgservice.validation.groups.OnCreatePractice;
import com.thanthu.orgservice.validation.groups.OnUpdatePracticeName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PracticeDto {
	
	private Long id;

	private LocalDateTime createdDateTime;

	private LocalDateTime updateDateTime;

	@NotNull(groups = { OnCreatePractice.class, OnUpdatePracticeName.class })
	private String name;

	private OrganizationDto organization;

	@Builder.Default
	private Set<UserDto> users = new HashSet<>();

}
