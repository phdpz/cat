package com.cat.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Admin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "admin", catalog = "cat")
public class Admin implements java.io.Serializable {

	// Fields

	private String AUserName;
	private String APassword;
	private String ASchool;
	private String AArea;
	private String AStuName;
	private String AStuPhone;
	private String AStuMail;

	// Constructors

	/** default constructor */
	public Admin() {
	}

	/** minimal constructor */
	public Admin(String AUserName, String APassword) {
		this.AUserName = AUserName;
		this.APassword = APassword;
	}

	/** full constructor */
	public Admin(String AUserName, String APassword, String ASchool,
			String AArea, String AStuName, String AStuPhone, String AStuMail) {
		this.AUserName = AUserName;
		this.APassword = APassword;
		this.ASchool = ASchool;
		this.AArea = AArea;
		this.AStuName = AStuName;
		this.AStuPhone = AStuPhone;
		this.AStuMail = AStuMail;
	}

	// Property accessors
	@Id
	@Column(name = "a_userName", unique = true, nullable = false)
	public String getAUserName() {
		return this.AUserName;
	}

	public void setAUserName(String AUserName) {
		this.AUserName = AUserName;
	}

	@Column(name = "a_password", nullable = false)
	public String getAPassword() {
		return this.APassword;
	}

	public void setAPassword(String APassword) {
		this.APassword = APassword;
	}

	@Column(name = "a_school")
	public String getASchool() {
		return this.ASchool;
	}

	public void setASchool(String ASchool) {
		this.ASchool = ASchool;
	}

	@Column(name = "a_area")
	public String getAArea() {
		return this.AArea;
	}

	public void setAArea(String AArea) {
		this.AArea = AArea;
	}

	@Column(name = "a_stuName")
	public String getAStuName() {
		return this.AStuName;
	}

	public void setAStuName(String AStuName) {
		this.AStuName = AStuName;
	}

	@Column(name = "a_stuPhone")
	public String getAStuPhone() {
		return this.AStuPhone;
	}

	public void setAStuPhone(String AStuPhone) {
		this.AStuPhone = AStuPhone;
	}

	@Column(name = "a_stuMail")
	public String getAStuMail() {
		return this.AStuMail;
	}

	public void setAStuMail(String AStuMail) {
		this.AStuMail = AStuMail;
	}

}