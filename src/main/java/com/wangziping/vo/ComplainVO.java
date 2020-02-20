package com.wangziping.vo;

public class ComplainVO {

	private String typename;
	private Integer startNum;
	private Integer endNum;
	private String startTime;
	private String endTime;
	private String orderName;
	private String orderMethod;

	public ComplainVO(String typename, Integer startNum, Integer endNum, String startTime, String endTime,
			String orderName, String orderMethod) {
		super();
		this.typename = typename;
		this.startNum = startNum;
		this.endNum = endNum;
		this.startTime = startTime;
		this.endTime = endTime;
		this.orderName = orderName;
		this.orderMethod = orderMethod;
	}

	public ComplainVO() {
		super();
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderMethod() {
		return orderMethod;
	}

	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}

	@Override
	public String toString() {
		return "ComplainVO [typename=" + typename + ", startNum=" + startNum + ", endNum=" + endNum + ", startTime="
				+ startTime + ", endTime=" + endTime + ", orderName=" + orderName + ", orderMethod=" + orderMethod
				+ "]";
	}

}
