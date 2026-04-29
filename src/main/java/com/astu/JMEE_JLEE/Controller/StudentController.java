package com.astu.JMEE_JLEE.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.astu.JMEE_JLEE.Service.StudentService;

@RestController
@RequestMapping("/ASTU_JMEE_JLEE/API/Student")
@CrossOrigin(origins = { "http://localhost:3000", "https://ssuhs.formsrec.in" })
public class StudentController {
	
	@Autowired
	private StudentService stu_ser;

	
	@PostMapping("/REGISTER")
	public ResponseEntity<?> registerUser(@RequestBody Map<String, String> data) {

		String result = stu_ser.registerStudent(data);
		if ("SUCCESS".equalsIgnoreCase(result)) {
	        return ResponseEntity.ok("Registered Successfully ✅");
	    } else {
	        return ResponseEntity.status(500).body("Registration Failed ❌");
	    }
	}
	
	@PostMapping("/LOGIN")
	public ResponseEntity<?> checkLoginCredentials(@RequestBody Map<String, String> data) {

		String result = stu_ser.checkLoginCredentials(data);
		if ("SUCCESS".equalsIgnoreCase(result)) {
	        return ResponseEntity.ok("Login Successfully ✅");
	    } else {
	        return ResponseEntity.status(500).body(result);
	    }
	}
	
	@PostMapping("/RESET_PASSWORD")
	public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
	    String username = req.get("username");
	    String newPassword = req.get("newPassword");
	    
	    String result = stu_ser.resetPassword(req);
	    System.out.println("mobileno"+username);
	    System.out.println("newPassword"+newPassword);

	    // logic here

	    return ResponseEntity.ok(Map.of("status", "success"));
	}
	
	

}