package com.astu.JMEE_JLEE.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astu.JMEE_JLEE.Service.SMService;

@RestController
@RequestMapping("/ASTU_JMEE_JLEE/API/Student")
@CrossOrigin(origins = { "http://localhost:3000", "https://ssuhs.formsrec.in" })
public class SMSOtpController {
	
	@Autowired
	private SMService sms_ser;
	
	
	//Registration
	@PostMapping("/SEND_MOBILE_OTP")
	public ResponseEntity<?> send_otp_to_mobile(@RequestParam String mobile)
	{
	    String result = sms_ser.sendOTPVerificationOnMobile(mobile);

	    if ("EXISTS".equals(result)) {
	        return ResponseEntity.badRequest().body("Mobile already registered ❌");
	    } else if ("SENT".equals(result)) {
	        return ResponseEntity.ok("OTP sent to mobile 📱");
	    } else {
	        return ResponseEntity.status(500).body("Failed to send OTP ❌");
	    }
	}
	
	@PostMapping("/VERIFY_MOBILE_OTP")
	public ResponseEntity<?> verifyMobileOtp(@RequestParam String mobile,@RequestParam String otp) {
	    boolean isValid = sms_ser.verifyMobileOtp(mobile, otp);
	    if (isValid) {
	        return ResponseEntity.ok("Verified");
	    } else {
	        return ResponseEntity.badRequest().body("Invalid OTP");
	    }
	}
	
	@PostMapping("/FORGOT_PASSWORD_SEND_OTP")
	public ResponseEntity<?> send_otp_to_forgotPassword(@RequestParam("username") String username) {

	    System.out.println("Forgot password API hit for: " + username);

	    String result = sms_ser.send_otp_to_forgotpassword(username);

	    if ("SUCCESS".equalsIgnoreCase(result)) {
	        return ResponseEntity.ok("OTP sent successfully ✅");
	    } else {
	        return ResponseEntity.status(400).body(result);
	    }
	}
	
	@PostMapping("/FORGOT_PASSWORD_VERIFY_MOBILE_OTP")
	public ResponseEntity<?> verifyMobileOtp_forgotPassword(@RequestParam String mobile,@RequestParam String otp) {
	    boolean isValid = sms_ser.verifyMobileOtp_forgotpassword(mobile, otp);
	    if (isValid) {
	        return ResponseEntity.ok("Verified");
	    } else {
	        return ResponseEntity.badRequest().body("Invalid OTP");
	    }
	}

}
