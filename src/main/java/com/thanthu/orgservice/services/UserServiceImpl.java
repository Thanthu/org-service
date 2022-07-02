package com.thanthu.orgservice.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.thanthu.orgservice.converters.UserDtoToUserConverter;
import com.thanthu.orgservice.converters.UserToUserDtoConverter;
import com.thanthu.orgservice.dtos.UserDto;
import com.thanthu.orgservice.exceptions.NotFoundException;
import com.thanthu.orgservice.model.User;
import com.thanthu.orgservice.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private final UserDtoToUserConverter userDtoToUserConverter;
	
	private final UserToUserDtoConverter userToUserDtoConverter;
	
	private final UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = userDtoToUserConverter.convert(userDto);
		User savedUser = saveUser(user);
		return userToUserDtoConverter.convert(savedUser);
	}

	@Override
	public Set<UserDto> getUsers(List<Long> ids, boolean showPractices, boolean showOrganization) {
		List<User> users = userRepository.findAllById(ids);
		return convertUsersToUserDtos(users, showPractices, showOrganization);
	}

	@Override
	public UserDto updateName(UserDto userDto) {
		User user = findById(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		User savedUser = saveUser(user);
		return userToUserDtoConverter.convert(savedUser);
	}

	@Override
	public Set<UserDto> findUsersByName(String name, boolean showPractices, boolean showOrganization) {
		List<User> users = userRepository.findAllByName(name.toLowerCase().trim());
		Set<UserDto> userDtos = convertUsersToUserDtos(users, showPractices, showOrganization);
		return userDtos;
	}

	@Override
	public UserDto findUserById(Long id, boolean showPractices, boolean showOrganization) {
		User user = findById(id);
		if(showOrganization) {
			return userToUserDtoConverter.convertWithPracticesAndOrganization(user);
		}
		
		if(showPractices) {
			return userToUserDtoConverter.convertWithPractices(user);
		}
		
		return userToUserDtoConverter.convert(user);
	}
	
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found."));
	}
	
	private Set<UserDto> convertUsersToUserDtos(List<User> users, boolean showPractices, boolean showOrganization) {
		if(showOrganization) {
			return users.stream()
					.map(user -> userToUserDtoConverter.convertWithPracticesAndOrganization(user))
					.collect(Collectors.toSet());
		}
		
		if(showPractices) {
			return users.stream()
					.map(user -> userToUserDtoConverter.convertWithPractices(user))
					.collect(Collectors.toSet());
		}
		
		return users.stream()
				.map(user -> userToUserDtoConverter.convert(user))
				.collect(Collectors.toSet());
	}

}
