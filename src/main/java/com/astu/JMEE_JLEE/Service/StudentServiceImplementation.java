package com.astu.JMEE_JLEE.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.astu.JMEE_JLEE.Entity.RegistrationDTO;
import com.astu.JMEE_JLEE.Entity.WebmasterDTO;
import com.astu.JMEE_JLEE.Repository.RegistrationRepo;
import com.astu.JMEE_JLEE.Repository.WebmasterRepo;
import com.astu.JMEE_JLEE.Service.SMService;

@Service
public class StudentServiceImplementation implements StudentService {

	@Autowired
	private RegistrationRepo registration_repo;
	@Autowired
	private SMService sms_service;
	@Autowired
	private WebmasterRepo master_repo;

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
			sms_service.sendRegistrationDataToStudent(newAppNo, password, mobile);
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
				System.out.println("email student row:"+stu);
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
	
	@Override
	public String saveProgram(String username,String program)
	{
		RegistrationDTO stu = null;
		WebmasterDTO master = null;
		try
		{
			if (username.matches("\\d{10}")) 
			{
				stu = registration_repo.findByMobileno(username);
				System.out.println("mobile number found:"+stu);
			} 
			else 
			{
				stu = registration_repo.findByEmail(username);
				System.out.println("email found:"+stu);
			}
			Long appNo = stu.getAppno();
			System.out.println("Appno is:"+appNo);
			master = master_repo.findByAppno(appNo);
			if(master==null)
			{
				master = new WebmasterDTO();
				master.setAppno(appNo);
			}
			master.setSelectprogram(program);
			if ("JOINT LATERAL ENTRY EXAMINATION (JLEE)-2026".equals(program)) 
			{
			    master.setProgrammecode(02);
			} 
			else if ("JOINT MCA ENTRANCE EXAMINATION (JMEE)-2026".equals(program)) {
			    master.setProgrammecode(01);;
			} 
			else {
			    throw new IllegalArgumentException("Invalid program selected: " + program);
			}
			master_repo.save(master);
			return "SUCCESS";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "FAIL";
		}
	}
	
	@Override
	public String getProgram(String username)
	{
		RegistrationDTO stu = null;
		WebmasterDTO master = null;
		String selected_program = "";
		try
		{
			if (username.matches("\\d{10}")) 
			{
				stu = registration_repo.findByMobileno(username);
				System.out.println("mobile number found:"+stu);
			} 
			else 
			{
				stu = registration_repo.findByEmail(username);
				System.out.println("email found:"+stu);
			}
			Long appNo = stu.getAppno();
			System.out.println("Appno is:"+appNo);
			master = master_repo.findByAppno(appNo);
			if(master!=null)
			{
				selected_program = master.getSelectprogram();
			}
			else
			{
				selected_program = "";
			}
			return selected_program;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public Map<String, Object> getUniqueData(String username)
	{
	    Map<String, Object> response = new HashMap<>();
	    RegistrationDTO stu = null;

	    try 
	    {
	        if (username.matches("\\d{10}")) 
	        {
	            stu = registration_repo.findByMobileno(username);
	        } 
	        else 
	        {
	            stu = registration_repo.findByEmail(username);
	        }

	        if (stu != null) 
	        {
	            response.put("appNo", stu.getAppno());
	            response.put("email", stu.getEmail());
	            response.put("mobileNo", stu.getMobileno());
	        }

	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }

	    return response;
	}
	
	

}
