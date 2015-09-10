package com.cat.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Station entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "station", catalog = "cat")
public class Station implements java.io.Serializable {

	// Fields

	private Integer SId;
	private String SSchool;
	private String SArea;

	// Constructors

	/** default constructor */
	public Station() {
	}

	/** full constructor */
	public Station(String SSchool, String SArea) {
		this.SSchool = SSchool;
		this.SArea = SArea;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "s_id", unique = true, nullable = false)
	public Integer getSId() {
		return this.SId;
	}

	public void setSId(Integer SId) {
		this.SId = SId;
	}

	@Column(name = "s_school")
	public String getSSchool() {
		return this.SSchool;
	}

	public void setSSchool(String SSchool) {
		this.SSchool = SSchool;
	}

	@Column(name = "s_area")
	public String getSArea() {
		return this.SArea;
	}

	public void setSArea(String SArea) {
		this.SArea = SArea;
	}

}