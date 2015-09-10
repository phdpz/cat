package com.cat.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Shopcart entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shopcart", catalog = "cat")
public class Shopcart implements java.io.Serializable {

	// Fields

	private Integer SId;
	private String openId;
	private Integer SNum;
	private Integer fruitId;

	// Constructors

	/** default constructor */
	public Shopcart() {
	}

	/** full constructor */
	public Shopcart(String openId, Integer SNum, Integer fruitId) {
		this.openId = openId;
		this.SNum = SNum;
		this.fruitId = fruitId;
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

	@Column(name = "openId")
	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Column(name = "s_num")
	public Integer getSNum() {
		return this.SNum;
	}

	public void setSNum(Integer SNum) {
		this.SNum = SNum;
	}

	@Column(name = "fruitId")
	public Integer getFruitId() {
		return this.fruitId;
	}

	public void setFruitId(Integer fruitId) {
		this.fruitId = fruitId;
	}

}