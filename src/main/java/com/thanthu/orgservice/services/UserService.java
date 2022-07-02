package com.thanthu.orgservice.services;

import java.util.List;
import java.util.Set;

import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.model.User;

public interface UserService {
	
	public UserDto createUser(UserDto userDto);

	public Set<UserDto> getUsers(List<Long> ids, boolean showPractices, boolean showOrganization);

	public UserDto updateName(UserDto userDto);
	
	public Set<UserDto> findUsersByName(String name, boolean showPractices, boolean showOrganization);

	public UserDto findUserById(Long id, boolean showPractices, boolean showOrganization);

	public User findById(Long id);

	public User saveUser(User user);

}
