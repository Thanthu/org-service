package com.thanthu.orgservice.dtos;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.thanthu.orgservice.enums.UserType;
import com.thanthu.orgservice.validation.groups.OnCreateUser;
import com.thanthu.orgservice.validation.groups.OnUpdateUserName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserDto {

	private Long id;

	@NotNull(groups = { OnCreateUser.class, OnUpdateUserName.class })
	private String firstName;

	@NotNull(groups = { OnCreateUser.class, OnUpdateUserName.class })
	private String lastName;

	private LocalDateTime createdDateTime;

	private LocalDateTime updateDateTime;

	@Builder.Default
	private Set<PracticeDto> practices = new HashSet<>();
	
	@NotNull(groups = { OnCreateUser.class })
	private UserType type;

}
