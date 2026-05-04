package com.astu.JMEE_JLEE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.astu.JMEE_JLEE.Entity.RegistrationDTO;

public interface RegistrationRepo extends JpaRepository<RegistrationDTO,Integer> {

	RegistrationDTO findByEmail(String email);
	RegistrationDTO findByMobileno(String mobile);
	
	@Query("SELECT MAX(r.appno) FROM RegistrationDTO r")
	Long findMaxAppNo();
	
	boolean existsByEmail(String email);
	boolean existsByMobileno(String mobile);
}
