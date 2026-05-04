package com.astu.JMEE_JLEE.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.astu.JMEE_JLEE.Service.StudentService;
import com.astu.JMEE_JLEE.TokenJWT.JwtUtil;

@RestController
@RequestMapping("/ASTU_JMEE_JLEE/API/Student")
@CrossOrigin(origins = { "http://localhost:3000", "https://ssuhs.formsrec.in" })
public class StudentController {
	
	@Autowired
	private StudentService stu_ser;
	
	@Autowired
	private JwtUtil jwtUtil;

	
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
	    System.out.println("login calls");
	    String result = stu_ser.checkLoginCredentials(data);
	    if ("SUCCESS".equalsIgnoreCase(result)) {
	        String username = data.get("username");
	        String token = jwtUtil.generateToken(username);
	        return ResponseEntity.ok().body(Map.of("message", "Login Successfully ✅", "token", token));
	    } 
	    else {
	        return ResponseEntity.status(400).body(Map.of("message", result));
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
	
	@GetMapping("/GET_PROGRAM")
	public ResponseEntity<?> getProgram(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
	    String username = jwtUtil.extractUsername(token);
	    String program = stu_ser.getProgram(username);
		return ResponseEntity.ok(program != null ? program : "");
	}
	
	@PostMapping("/SAVE_PROGRAM")
	public ResponseEntity<Map<String, Object>> saveProgram(@RequestHeader("Authorization") String authHeader,@RequestBody Map<String, String> data) {
	    Map<String, Object> response = new HashMap<>();
	    try 
	    {
	        String token = authHeader.substring(7);
	        String username = jwtUtil.extractUsername(token);
	        String program = data.get("program");
	        String result = stu_ser.saveProgram(username, program);
	        if ("SUCCESS".equals(result)) 
	        {
	            response.put("status", "SUCCESS");
	            response.put("message", "Program saved successfully ✅");
	            response.put("program", program);

	            return ResponseEntity.ok(response);
	        } 
	        else 
	        {
	            response.put("status", "FAIL");
	            response.put("message", result);
	            return ResponseEntity.badRequest().body(response);
	        }

	    } 
	    catch (Exception e) 
	    {
	        response.put("status", "ERROR");
	        response.put("message", "Something went wrong ❗");
	        return ResponseEntity.internalServerError().body(response);
	    }
	}
	
	@GetMapping("/GET_UNIQUE_DATA")
	public ResponseEntity<?> getUniqueData(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
	    String username = jwtUtil.extractUsername(token);
	    Map<String, Object> data = stu_ser.getUniqueData(username);
	    System.out.println(data);
	    return ResponseEntity.ok(data);
	}
	
	
	
	

}