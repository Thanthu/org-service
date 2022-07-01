package com.thanthu.orgservice.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.services.UserService;
import com.thanthu.orgservice.validation.groups.OnCreateUser;
import com.thanthu.orgservice.validation.groups.OnUpdateUserName;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
@Validated
public class UserController {
	
	private final UserService userService;

	@PostMapping
	@Validated(OnCreateUser.class)
	public UserDto createUser(@Valid @RequestBody UserDto userDto) {
		return userService.createUser(userDto);
	}

	@GetMapping("/list-by-ids")
	public Set<UserDto> getUsers(@RequestParam(required = true) List<Long> ids,
			@RequestParam(defaultValue = "false") boolean showPractices,
			@RequestParam(defaultValue = "false") boolean showOrganization) {
		return userService.getUsers(ids, showPractices, showOrganization);
	}

	@PutMapping("/{id}/name")
	@Validated(OnUpdateUserName.class)
	public UserDto updateName(@Valid @RequestBody UserDto userDto, @PathVariable Long id) {
		userDto.setId(id);
		return userService.updateName(userDto);
	}

	@GetMapping("/list-by-name")
	public Set<UserDto> findUsersByName(@RequestParam(required = true) String name,
			@RequestParam(defaultValue = "false") boolean showPractices,
			@RequestParam(defaultValue = "false") boolean showOrganization) {
		return userService.findUsersByName(name, showPractices, showOrganization);
	}

	@GetMapping("/{id}")
	public UserDto findUserById(@PathVariable Long id,
			@RequestParam(defaultValue = "false") boolean showPractices,
			@RequestParam(defaultValue = "false") boolean showOrganization) {
		return userService.findUserById(id, showPractices, showOrganization);
	}

}
