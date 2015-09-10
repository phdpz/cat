package com.cat.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clientinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "clientinfo", catalog = "cat")
public class Clientinfo implements java.io.Serializable {

	// Fields

	private String openId;
	private String nickName;
	private String sex;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private Double scroe;

	// Constructors

	/** default constructor */
	public Clientinfo() {
	}

	/** minimal constructor */
	public Clientinfo(String openId) {
		this.openId = openId;
	}

	/** full constructor */
	public Clientinfo(String openId, String nickName, String sex, String city,
			String province, String country, String headimgurl, Double scroe) {
		this.openId = openId;
		this.nickName = nickName;
		this.sex = sex;
		this.city = city;
		this.province = province;
		this.country = country;
		this.headimgurl = headimgurl;
		this.scroe = scroe;
	}

	// Property accessors
	@Id
	@Column(name = "openId", unique = true, nullable = false)
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "nickName")
	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "sex", length = 4)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "city")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "province")
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "country")
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "headimgurl")
	public String getHeadimgurl() {
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	@Column(name = "scroe", precision = 22, scale = 0)
	public Double getScroe() {
		return this.scroe;
	}

	public void setScroe(Double scroe) {
		this.scroe = scroe;
	}

}