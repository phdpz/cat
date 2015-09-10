package com.cat.pojo;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Orderinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "orderinfo", catalog = "cat")
public class Orderinfo implements java.io.Serializable {

	// Fields

	private String orderNum;
	private String weOrderNum;
	private String openId;
	private Double totalFee;
	private String fruit;
	private Timestamp orderTime;
	private String status;
	private String code;
	private String clientName;
	private String school;
	private String area;
	private String phone;

	// Constructors

	/** default constructor */
	public Orderinfo() {
	}

	/** minimal constructor */
	public Orderinfo(String orderNum) {
		this.orderNum = orderNum;
	}

	/** full constructor */
	public Orderinfo(String orderNum, String weOrderNum, String openId,
			Double totalFee, String fruit, Timestamp orderTime, String status,
			String code, String clientName, String school, String area,
			String phone) {
		this.orderNum = orderNum;
		this.weOrderNum = weOrderNum;
		this.openId = openId;
		this.totalFee = totalFee;
		this.fruit = fruit;
		this.orderTime = orderTime;
		this.status = status;
		this.code = code;
		this.clientName = clientName;
		this.school = school;
		this.area = area;
		this.phone = phone;
	}

	// Property accessors
	@Id
	@Column(name = "orderNum", unique = true, nullable = false)
	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "weOrderNum")
	public String getWeOrderNum() {
		return this.weOrderNum;
	}

	public void setWeOrderNum(String weOrderNum) {
		this.weOrderNum = weOrderNum;
	}

	@Column(name = "openId")
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "total_fee", precision = 16, scale = 0)
	public Double getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "fruit", length = 65535)
	public String getFruit() {
		return this.fruit;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}

	@Column(name = "orderTime", length = 19)
	public Timestamp getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "code")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "clientName")
	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Column(name = "school")
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "area")
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}