package com.yxld.yxchuangxin.entity;

/**
 * CxwyXiangmu entity. @author MyEclipse Persistence Tools
 */

public class CxwyXiangmu {

	// Fields

	private Integer xiangmuId; //id
	private String xiangmuLoupan; //楼盘
	private String xiangmuLd; //楼栋
	private String xiangmuDy; // 单元
	private String xiangmuZimupingyin;
	private Integer xiangmuParentId;  //父级id
	
	//经纬度
	private  String xiangmuLatitude; //纬度
    private String  xiangmuLongitude; //经度
	// Constructors

	/** default constructor */
	public CxwyXiangmu() {
	}

	/** full constructor */
	public CxwyXiangmu(String xiangmuLoupan, String xiangmuLd,
			String xiangmuDy, String xiangmuZimupingyin, Integer xiangmuParentId,String xiangmuLatitude,String xiangmuLongitude) {
		this.xiangmuLoupan = xiangmuLoupan;
		this.xiangmuLd = xiangmuLd;
		this.xiangmuDy = xiangmuDy;
		this.xiangmuZimupingyin = xiangmuZimupingyin;
		this.xiangmuParentId = xiangmuParentId;
		this.xiangmuLongitude=xiangmuLongitude;
		this.xiangmuLatitude=xiangmuLatitude;
		
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

	public String getXiangmuLd() {
		return this.xiangmuLd;
	}

	public void setXiangmuLd(String xiangmuLd) {
		this.xiangmuLd = xiangmuLd;
	}

	public String getXiangmuDy() {
		return this.xiangmuDy;
	}

	public void setXiangmuDy(String xiangmuDy) {
		this.xiangmuDy = xiangmuDy;
	}

	public String getXiangmuZimupingyin() {
		return this.xiangmuZimupingyin;
	}

	public void setXiangmuZimupingyin(String xiangmuZimupingyin) {
		this.xiangmuZimupingyin = xiangmuZimupingyin;
	}

	public Integer getXiangmuParentId() {
		return this.xiangmuParentId;
	}

	public void setXiangmuParentId(Integer xiangmuParentId) {
		this.xiangmuParentId = xiangmuParentId;
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

	@Override
	public String toString() {
		return "CxwyXiangmu [xiangmuId=" + xiangmuId + ", xiangmuLoupan="
				+ xiangmuLoupan + ", xiangmuLd=" + xiangmuLd + ", xiangmuDy="
				+ xiangmuDy + ", xiangmuZimupingyin=" + xiangmuZimupingyin
				+ ", xiangmuParentId=" + xiangmuParentId + ", xiangmuLatitude="
				+ xiangmuLatitude + ", xiangmuLongitude=" + xiangmuLongitude
				+ "]";
	}
}