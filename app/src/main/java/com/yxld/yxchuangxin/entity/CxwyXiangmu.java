package com.yxld.yxchuangxin.entity;

/**
 * CxwyXiangmu entity. @author Carrol
 */

public class CxwyXiangmu {
	// Fields
	private Integer xiangmuId; //id
	private String xiangmuLoupan; //楼盘
	private String xiangmuZimupingyin;
	private String xiangmuTelphone;//电话号码
	
	//经纬度
	private  String xiangmuLatitude; //纬度
    private String  xiangmuLongitude; //经度
    private Integer xiangmuIsUse;
	// Constructors

	/** default constructor */
	public CxwyXiangmu() {
	}

	/** full constructor */
	public CxwyXiangmu(String xiangmuLoupan, String xiangmuLd,
			 String xiangmuZimupingyin, String xiangmuLatitude, String xiangmuLongitude, String xiangmuTelphone) {
		this.xiangmuLoupan = xiangmuLoupan;
		this.xiangmuZimupingyin = xiangmuZimupingyin;
		this.xiangmuLongitude=xiangmuLongitude;
		this.xiangmuLatitude=xiangmuLatitude;
		this.xiangmuTelphone=xiangmuTelphone;
		
	}

	// Property accessors

	public Integer getXiangmuId() {
		return this.xiangmuId;
	}

	public void setXiangmuId(Integer xiangmuId) {
		this.xiangmuId = xiangmuId;
	}

	public String getXiangmuLoupan() {
		return this.xiangmuLoupan;
	}

	public void setXiangmuLoupan(String xiangmuLoupan) {
		this.xiangmuLoupan = xiangmuLoupan;
	}

	public String getXiangmuZimupingyin() {
		return this.xiangmuZimupingyin;
	}

	public void setXiangmuZimupingyin(String xiangmuZimupingyin) {
		this.xiangmuZimupingyin = xiangmuZimupingyin;
	}

	public String getXiangmuLatitude() {
		return xiangmuLatitude;
	}

	public void setXiangmuLatitude(String xiangmuLatitude) {
		this.xiangmuLatitude = xiangmuLatitude;
	}

	public String getXiangmuLongitude() {
		return xiangmuLongitude;
	}

	public void setXiangmuLongitude(String xiangmuLongitude) {
		this.xiangmuLongitude = xiangmuLongitude;
	}

	public String getXiangmuTelphone() {
		return xiangmuTelphone;
	}

	public void setXiangmuTelphone(String xiangmuTelphone) {
		this.xiangmuTelphone = xiangmuTelphone;
	}

	public Integer getXiangmuIsUse() {
		return xiangmuIsUse;
	}

	public void setXiangmuIsUse(Integer xiangmuIsUse) {
		this.xiangmuIsUse = xiangmuIsUse;
	}

}