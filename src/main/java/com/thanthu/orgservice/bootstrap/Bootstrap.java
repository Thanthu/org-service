package com.thanthu.orgservice.bootstrap;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.thanthu.orgservice.enums.UserType;
import com.thanthu.orgservice.model.Organization;
import com.thanthu.orgservice.model.Practice;
import com.thanthu.orgservice.model.User;
import com.thanthu.orgservice.repositories.OrganizationRepository;
import com.thanthu.orgservice.repositories.PracticeRepository;
import com.thanthu.orgservice.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
	
	private final OrganizationRepository organizationRepository;
	
	private final PracticeRepository practiceRepository;
	
	private final UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		log.info("Started loading bootstrap data");
		
		Organization organization1 = Organization.builder()
				.id(1L)
				.name("Organization 1")
				.build();
		organizationRepository.save(organization1);
		
		User user1 = User.builder()
				.firstName("Thanthu")
				.lastName("Nair")
				.type(UserType.DOCTOR)
				.build();
		user1 = userRepository.save(user1);
		
		User user2 = User.builder()
				.firstName("John")
				.lastName("Doe")
				.type(UserType.STAFF)
				.build();
		user2 = userRepository.save(user2);
		
		Set<User> users = Stream.of(user1, user2).collect(Collectors.toSet());
		Practice practice1 = Practice.builder()
				.id(1L)
				.name("Practice 1")
				.organization(organization1)
				.users(users)
				.build();
		practice1 = practiceRepository.save(practice1);
		
		Practice practice2 = Practice.builder()
				.id(2L)
				.name("Practice 2")
				.organization(organization1)
				.users(users)
				.build();
		practice2 = practiceRepository.save(practice2);
		
		log.info("Finished loading bootstrap data");
		
	}
	

}
