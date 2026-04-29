package com.astu.JMEE_JLEE.Service;

import java.util.Map;

public interface StudentService {
	
	public String registerStudent(Map<String, String> data);
	public String checkLoginCredentials(Map<String, String> data);
	public String resetPassword(Map<String, String> data);
	

}
