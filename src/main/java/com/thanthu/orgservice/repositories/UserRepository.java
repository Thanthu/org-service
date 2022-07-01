package com.thanthu.orgservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thanthu.orgservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where lower(concat(u.firstName, ' ', u.lastName)) like %:name%")
	public List<User> findAllByName(@Param("name") String name);

}
