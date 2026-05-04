package com.astu.JMEE_JLEE.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.astu.JMEE_JLEE.Entity.RegistrationDTO;
import com.astu.JMEE_JLEE.Repository.RegistrationRepo;

@Service
public class SMServiceImplementation implements SMService {

	private final RestTemplate restTemplate = new RestTemplate();
	private Map<String, String> otpMap = new HashMap<>();
	private Map<String, String> otpMap_forgotpassword = new HashMap<>();
	
	@Autowired
	private RegistrationRepo register_Repo;

	@Override
	public String sendOTPVerificationOnMobile(String mobile) {
		try {
			if (register_Repo.existsByMobileno(mobile)) {
				System.out.println("existss");
				return "EXISTS";
			}
			int otp = (int) (Math.random() * 900000) + 100000;
			System.out.println("otp is:" + otp);
			String msg = "Dear Candidate Your OTP - " + otp + " from ASTU_JMEE_JLEE - Powered by SMB";
			String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());
			otpMap.put(mobile, String.valueOf(otp));

			String url = "https://webpostservice.com/sendsms_v2.0/sendsms.php" + "?apikey=c21iVDpiNVZ0azN3Qg=="
					+ "&type=TEXT" + "&sender=SMBONL" + "&mobile=" + mobile + "&message=" + encodedMsg
					+ "&peId=1201159221747662805" + "&tempId=1307161518438747032";

			String response = restTemplate.getForObject(url, String.class);
			System.out.println("response" + response);
			if (response != null && response.contains("SUCCESS")) {
	            return "SENT";
	        } else {
	            return "ERROR";
	        }

		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR";
		}

	}

	@Override
	public boolean verifyMobileOtp(String mobile, String enteredOtp) {
		String storedOtp = otpMap.get(mobile);
		if (storedOtp == null)
			return false;
		if (storedOtp.equals(enteredOtp)) {
			otpMap.remove(mobile); // 🔥 remove after success
			return true;
		}

		return false;
	}
	
	
	@Override
	public String send_otp_to_forgotpassword(String username)
	{
		try
		{
			RegistrationDTO stu = null;
			if (username.matches("\\d{10}")) 
			{
				stu = register_Repo.findByMobileno(username);
			} 
		
			if (stu == null) 
			{
				return "USER_NOT_FOUND";
			}
			
			String mobile = stu.getMobileno();
	        int otp = (int) (Math.random() * 900000) + 100000;
	        System.out.println("OTP is: " + otp);
	        otpMap_forgotpassword.put(mobile, String.valueOf(otp));

	        String msg = "Dear Candidate Your OTP - " + otp + " from ASTU_JMEE_JLEE - Powered by SMB";
	        String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());

	        String url = "https://webpostservice.com/sendsms_v2.0/sendsms.php"
	                + "?apikey=c21iVDpiNVZ0azN3Qg=="
	                + "&type=TEXT"
	                + "&sender=SMBONL"
	                + "&mobile=" + mobile
	                + "&message=" + encodedMsg
	                + "&peId=1201159221747662805"
	                + "&tempId=1307161518438747032";

	        String response = restTemplate.getForObject(url, String.class);
	        System.out.println("SMS Response: " + response);
	        if (response != null && response.contains("SUCCESS")) {
	            return "SUCCESS";
	        } else {
	            return "OTP_FAILED";
	        }
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "ERROR";
			
		}
	}
	
	@Override
	public boolean verifyMobileOtp_forgotpassword(String mobile, String enteredOtp) {
		String storedOtp = otpMap_forgotpassword.get(mobile);
		if (storedOtp == null)
			return false;
		if (storedOtp.equals(enteredOtp)) {
			otpMap_forgotpassword.remove(mobile); 
			return true;
		}

		return false;
	}
	
	@Override
	public void sendRegistrationDataToStudent(Long applicationumber,String password,String mobile)
	{
		try
		{
				String msg="Dear Candidate - Your user name -"+applicationumber+" and password -"+password+" from ASTU_JMEE_JLEE - Powered by SMB";
	            String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());
	            String url = "https://webpostservice.com/sendsms_v2.0/sendsms.php" +
	                    "?apikey=c21iVDpiNVZ0azN3Qg==" +
	                    "&type=TEXT" +
	                    "&sender=SMBONL" +
	                    "&mobile=" + mobile +
	                    "&message=" + msg +
	                    "&peId=1201159221747662805" +
	                    "&tempId=1307161518429402176";
	            String response = restTemplate.getForObject(url, String.class);
	            System.out.println("application number SMS Response: " + response);
			   		           
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
