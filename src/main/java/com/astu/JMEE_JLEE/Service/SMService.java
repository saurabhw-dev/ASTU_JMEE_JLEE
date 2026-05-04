package com.astu.JMEE_JLEE.Service;

public interface SMService {
	//for registration
	public String sendOTPVerificationOnMobile(String mobile);
	public boolean verifyMobileOtp(String mobile, String enteredOtp);
	
	//for forgotpassword
	public String send_otp_to_forgotpassword(String username);
	public boolean verifyMobileOtp_forgotpassword(String mobile, String enteredOtp);
	public void sendRegistrationDataToStudent(Long applicationumber,String password,String mobile);
		

}
