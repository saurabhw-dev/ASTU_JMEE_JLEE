package com.astu.JMEE_JLEE.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.astu.JMEE_JLEE.Entity.RegistrationDTO;
import com.astu.JMEE_JLEE.Repository.RegistrationRepo;

@Service
public class StudentServiceImplementation implements StudentService {

	@Autowired
	private RegistrationRepo registration_repo;

	@Override
	public String registerStudent(Map<String, String> data) {
		try 
		{
			System.out.println("REGISTER API HIT ✅");
			String email = data.get("email");
			String mobile = data.get("mobile");
			String password = data.get("password");
			String year = data.get("academicYear");
			
			
			RegistrationDTO registerstu = new RegistrationDTO();
			Long lastAppNo = registration_repo.findMaxAppNo();
			long newAppNo;
			if (lastAppNo == null || lastAppNo == 0) {
			    newAppNo = 262000001L; 
			} else {
			    newAppNo = lastAppNo + 1;
			}

			registerstu.setAppno(newAppNo);
			registerstu.setAcademicyear(year);
			registerstu.setEmail(email);
			registerstu.setMobileno(mobile);
			registerstu.setConfirmpassword(password);

			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encryptedPassword = passwordEncoder.encode(password);
			registerstu.setPassword(encryptedPassword);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
			String formattedDateTime = LocalDateTime.now().format(formatter);
			registerstu.setRegistration_Date_Time(formattedDateTime);
			registration_repo.save(registerstu);
			return "SUCCESS";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAIL";
		}
	}
	
	@Override
	public String checkLoginCredentials(Map<String, String> data)
	{
		String username = data.get("username");
		String enteredpassword = data.get("password");
		
		try
		{
			RegistrationDTO stu = null;
			if (username.matches("\\d{10}")) 
			{
				stu = registration_repo.findByMobileno(username);
			} 
			else 
			{
				stu = registration_repo.findByEmail(username);
			}
			
			if (stu == null) 
			{
				return "USER_NOT_FOUND";
			}
			
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(enteredpassword, stu.getPassword())) 
			{
				return "SUCCESS";
			} 
			else 
			{
				return "INVALID_PASSWORD";
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	@Override
	public String resetPassword(Map<String, String> data)
	{
		try
		{
			String username = data.get("username");
		    String newPassword = data.get("newPassword");
		    RegistrationDTO stu = null;
		    if (username.matches("\\d{10}")) 
			{
				stu = registration_repo.findByMobileno(username);
			}
		    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		    stu.setPassword(passwordEncoder.encode(newPassword));
		    stu.setConfirmpassword(newPassword);
		    registration_repo.save(stu);
		    
		    return "success";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
	}
	
	

}
