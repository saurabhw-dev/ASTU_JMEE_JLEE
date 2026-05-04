package com.astu.JMEE_JLEE.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.astu.JMEE_JLEE.Repository.RegistrationRepo;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImplementation implements EmailService{
	
	private static final Map<String, String> otpStore = new HashMap<>();
	
	@Autowired
	private RegistrationRepo register_Repo;

	
	@Override
	public String sendOTPVerificatioEmail(String email) {
		
		String emailAWSHost = "email-smtp.ap-south-1.amazonaws.com";
		String emailAWSUser = "AKIAW3MEEJSHAKPSGVVQ";
		String emailAWSPassword = "BFFk1ozcSpzlmRCO1qss2ZChJ0kDzrrISivk9XDpI/fz";
		String emailAWSPort = "587";
		String emailSendFrom = "abhijit.b@smbgroup.co.in"; // must be verified in AWS

	    try {
	    	
	    	if (register_Repo.existsByEmail(email)) {
	            return "EXISTS";
	        }
	        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
	        String subject = "🔐 OTP Verification - Secure Access ASTU JMEE-JLEE";
	        String body =
	        		"Hello User,<br><br>" +

	        		"🔐 Your One-Time Password (OTP) is: " +
	        		"<b style='color:green; font-size:16px;'>" + otp + "</b><br><br>" +

	        		"⚠️ Please do not share this OTP with anyone.<br><br>" +

	        		"If you did not request this, please ignore this email.<br><br>" +

	        		"<b>Regards,</b><br>" +
	        		"<b>ASTU Support Team</b>";
	       
	        Properties props = new Properties();
	        props.put("mail.smtp.host", emailAWSHost);
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.starttls.required", "true");
	        props.put("mail.smtp.ssl.trust", emailAWSHost);

	        Session session = Session.getInstance(props);
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(emailSendFrom));
	        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
	        msg.setSubject(subject);
	        msg.setContent(body, "text/html; charset=UTF-8");

	        Transport transport = session.getTransport("smtp");
	        transport.connect(emailAWSHost, emailAWSUser, emailAWSPassword);
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();

	        System.out.println("OTP Sent Successfully: " + otp);
	        otpStore.put(email, otp);
	        return "SENT";

	    } catch (Exception ex) {
	        System.out.println("Email not sent: " + ex.getMessage());
	        return "ERROR";
	    }
	}
	
	@Override
	public boolean verifyEMAILOTP(String email, String enteredOtp) {

	    String storedOtp = otpStore.get(email);

	    if (storedOtp == null) return false;

	    if (storedOtp.equals(enteredOtp)) {
	        otpStore.remove(email); 
	        return true;
	    }

	    return false;
	}
	
	
}

