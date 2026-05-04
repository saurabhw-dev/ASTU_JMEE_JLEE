package com.astu.JMEE_JLEE.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="webmaster")
public class WebmasterDTO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	@Column(unique=true)
	private long appno;
	@Column(length=70,name="programme")
	private String selectprogram;
	@Column(name="programme_code")
	private Integer programmecode;
	
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
	public String getSelectprogram() {
		return selectprogram;
	}
	public void setSelectprogram(String selectprogram) {
		this.selectprogram = selectprogram;
	}
	public Integer getProgrammecode() {
		return programmecode;
	}
	public void setProgrammecode(Integer programmecode) {
		this.programmecode = programmecode;
	}
}
