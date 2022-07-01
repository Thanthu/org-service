package com.thanthu.orgservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thanthu.orgservice.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
	
	@Query("select o from Organization o where lower(o.name) like %:name%")
	public List<Organization> findAllByName(String name);

}
