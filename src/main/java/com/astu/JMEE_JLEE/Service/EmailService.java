package com.astu.JMEE_JLEE.Service;

public interface EmailService {
	
	//Email
	public String sendOTPVerificatioEmail(String email);
	public boolean verifyEMAILOTP(String email, String enteredOtp);

}
