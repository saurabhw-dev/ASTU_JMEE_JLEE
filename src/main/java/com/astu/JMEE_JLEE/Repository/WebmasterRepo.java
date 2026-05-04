package com.astu.JMEE_JLEE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astu.JMEE_JLEE.Entity.WebmasterDTO;

public interface WebmasterRepo extends JpaRepository<WebmasterDTO,Integer> {
	
	WebmasterDTO findByAppno(Long appno);

}
