package com.thanthu.orgservice.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.thanthu.orgservice.validation.groups.OnCreateOrganization;
import com.thanthu.orgservice.validation.groups.OnUpdateOrganizationName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class OrganizationDto {
	
	private Long id;
	
	private LocalDateTime createdDateTime;
	
	private LocalDateTime updateDateTime;
	
	@NotNull(groups = { OnCreateOrganization.class, OnUpdateOrganizationName.class })
	private String name;
	
	@Builder.Default
	private Set<PracticeDto> practices = new HashSet<>();

}
