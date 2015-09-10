package com.cat.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Fruitgoods entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fruitgoods", catalog = "cat")
public class Fruitgoods implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FGodsName;
	private String FUnit;
	private Integer FWeight;
	private String FFruitType;
	private String FLocation;
	private Double FRealFee;
	private Double FMarketFee;
	private String FDetail;
	private String FImg;
	private String FStatus;
	private Short FNative;
	private String FIntroduce;

	// Constructors

	/** default constructor */
	public Fruitgoods() {
	}

	/** full constructor */
	public Fruitgoods(String FGodsName, String FUnit, Integer FWeight,
			String FFruitType, String FLocation, Double FRealFee,
			Double FMarketFee, String FDetail, String FImg, String FStatus,
			Short FNative, String FIntroduce) {
		this.FGodsName = FGodsName;
		this.FUnit = FUnit;
		this.FWeight = FWeight;
		this.FFruitType = FFruitType;
		this.FLocation = FLocation;
		this.FRealFee = FRealFee;
		this.FMarketFee = FMarketFee;
		this.FDetail = FDetail;
		this.FImg = FImg;
		this.FStatus = FStatus;
		this.FNative = FNative;
		this.FIntroduce = FIntroduce;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "f_id", unique = true, nullable = false)
	public Integer getFId() {
		return this.FId;
	}

	public void setFId(Integer FId) {
		this.FId = FId;
	}

	@Column(name = "f_godsName")
	public String getFGodsName() {
		return this.FGodsName;
	}

	public void setFGodsName(String FGodsName) {
		this.FGodsName = FGodsName;
	}

	@Column(name = "f_unit")
	public String getFUnit() {
		return this.FUnit;
	}

	public void setFUnit(String FUnit) {
		this.FUnit = FUnit;
	}

	@Column(name = "f_weight")
	public Integer getFWeight() {
		return this.FWeight;
	}

	public void setFWeight(Integer FWeight) {
		this.FWeight = FWeight;
	}

	@Column(name = "f_fruitType")
	public String getFFruitType() {
		return this.FFruitType;
	}

	public void setFFruitType(String FFruitType) {
		this.FFruitType = FFruitType;
	}

	@Column(name = "f_location")
	public String getFLocation() {
		return this.FLocation;
	}

	public void setFLocation(String FLocation) {
		this.FLocation = FLocation;
	}

	@Column(name = "f_realFee", precision = 16, scale = 0)
	public Double getFRealFee() {
		return this.FRealFee;
	}

	public void setFRealFee(Double FRealFee) {
		this.FRealFee = FRealFee;
	}

	@Column(name = "f_marketFee", precision = 16, scale = 0)
	public Double getFMarketFee() {
		return this.FMarketFee;
	}

	public void setFMarketFee(Double FMarketFee) {
		this.FMarketFee = FMarketFee;
	}

	@Column(name = "f_detail", length = 65535)
	public String getFDetail() {
		return this.FDetail;
	}

	public void setFDetail(String FDetail) {
		this.FDetail = FDetail;
	}

	@Column(name = "f_img")
	public String getFImg() {
		return this.FImg;
	}

	public void setFImg(String FImg) {
		this.FImg = FImg;
	}

	@Column(name = "f_status")
	public String getFStatus() {
		return this.FStatus;
	}

	public void setFStatus(String FStatus) {
		this.FStatus = FStatus;
	}

	@Column(name = "f_native")
	public Short getFNative() {
		return this.FNative;
	}

	public void setFNative(Short FNative) {
		this.FNative = FNative;
	}

	@Column(name = "f_introduce", length = 65535)
	public String getFIntroduce() {
		return this.FIntroduce;
	}

	public void setFIntroduce(String FIntroduce) {
		this.FIntroduce = FIntroduce;
	}

}