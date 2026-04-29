package com.astu.JMEE_JLEE.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="registration")
public class RegistrationDTO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	@Column(unique=true)
	private long appno;
	@Column(length=10)
	private String academicyear;
	@Column(length=100,unique=true)
	private String email;
	@Column(length=11,unique=true)
	private String mobileno;
	@Column
	private String password;
	@Column(length=30)
	private String confirmpassword;
	@Column(length=50)
	private String registration_Date_Time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getAppno() {
		return appno;
	}
	public void setAppno(long appno) {
		this.appno = appno;
	}
	public String getAcademicyear() {
		return academicyear;
	}
	public void setAcademicyear(String academicyear) {
		this.academicyear = academicyear;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	public String getRegistration_Date_Time() {
		return registration_Date_Time;
	}
	public void setRegistration_Date_Time(String registration_Date_Time) {
		this.registration_Date_Time = registration_Date_Time;
	}
	
	
	
	
	
	


}
