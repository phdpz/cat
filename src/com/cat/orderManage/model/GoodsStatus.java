package com.cat.orderManage.model;

public class GoodsStatus {
	private Integer fruitId;
	private String fruitName;
	private Integer num;
	private Integer totalWeight;
	private Double realFee;
	private Integer weight;
	private String fruitType;
	private String location;
	private Double radio;
	private Double radio1;
	private Double totalFee;
	
	public GoodsStatus(){
	}

	public Integer getFruitId() {
		return fruitId;
	}

	public void setFruitId(Integer fruitId) {
		this.fruitId = fruitId;
	}

	public String getFruitName() {
		return fruitName;
	}

	public void setFruitName(String fruitName) {
		this.fruitName = fruitName; 
	}

	public Integer getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Integer totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getRealFee() {
		return realFee;
	}

	public void setRealFee(Double realFee) {
		this.realFee = realFee;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFruitType() {
		return fruitType;
	}

	public void setFruitType(String fruitType) {
		this.fruitType = fruitType;
	}

	public Double getRadio() {
		return radio;
	}

	public void setRadio(Double radio) {
		this.radio = radio;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Double getRadio1() {
		return radio1;
	}

	public void setRadio1(Double radio1) {
		this.radio1 = radio1;
	}	
}
