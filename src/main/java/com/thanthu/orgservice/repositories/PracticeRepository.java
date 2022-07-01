package com.thanthu.orgservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thanthu.orgservice.model.Practice;

public interface PracticeRepository extends JpaRepository<Practice, Long> {
	
	@Query("select p from Practice p where lower(p.name) like %:name%")
	public List<Practice> findAllByName(String name);

}
