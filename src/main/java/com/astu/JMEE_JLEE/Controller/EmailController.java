package com.astu.JMEE_JLEE.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astu.JMEE_JLEE.Service.EmailService;

@RestController
@RequestMapping("/ASTU_JMEE_JLEE/API/Student")
@CrossOrigin(origins = { "http://localhost:3000", "https://ssuhs.formsrec.in" })
public class EmailController {

	@Autowired
	private EmailService email_ser;
	
	@PostMapping("/SEND_OTP_TO_MAIL")
	public ResponseEntity<?> send_otp_to_email(@RequestParam String email)
	{
	    String result = email_ser.sendOTPVerificatioEmail(email);
	    if ("EXISTS".equals(result)) 
	    {
	        return ResponseEntity.badRequest().body("Email already registered ❌");
	    } 
	    else if ("SENT".equals(result))
	    {
	        return ResponseEntity.ok("OTP sent successfully 📧");
	    } 
	    else
	    {
	        return ResponseEntity.status(500).body("Failed to send OTP ❌");
	    }
	}
	
	@PostMapping("/VERIFY_EMAIL_OTP")
	public ResponseEntity<?> verifyEmailOtp(@RequestParam String email,@RequestParam String otp) {
		System.out.println("Verify EMAIL OTP ss");
		boolean isValid = email_ser.verifyEMAILOTP(email, otp);
	    if (isValid) {
	        return ResponseEntity.ok("Verified");
	    } else {
	        return ResponseEntity.badRequest().body("Invalid OTP");
	    }
	}
}
