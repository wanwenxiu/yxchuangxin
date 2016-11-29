package com.yxld.yxchuangxin.entity;


import java.util.List;

/**
 * @ClassName: AppYezhuFangwu
 * @Description:  App业主房屋中间表
 * @author wwx
 * @date 2016-11-24 下午6:09:20
 */
public class AppYezhuFangwu implements java.io.Serializable{

	private List<AppYezhuFangwu> rows;

	private Integer yezhuId; //业主id

	/** 房屋ID*/
	private Integer fwId;
	/** 房屋楼栋*/
	private String fwLoudong;
	/** 房屋单元*/
	private String fwDanyuan;
	/** 房号*/
	private String fwFanghao;
	/** 房屋户型*/
	private String fwHuxing;
	/** 房屋面积*/
	private String fwMainji;
	/** 楼盘ID*/
	private Integer fwLoupanId;
	/** 业主房屋关系类型：0产权人1家属2租客3其他4.历史产权人*/
	private Integer fwyzType;

	/** 房屋楼盘名称*/
	private String xiangmuLoupan; //楼盘
	/** 小区电话*/
	private String xiangmuTelphone;//电话号码

	public AppYezhuFangwu() {
	}

	public Integer getYezhuId() {
		return yezhuId;
	}

	public void setYezhuId(Integer yezhuId) {
		this.yezhuId = yezhuId;
	}

	public Integer getFwId() {
		return fwId;
	}

	public void setFwId(Integer fwId) {
		this.fwId = fwId;
	}

	public String getFwLoudong() {
		return fwLoudong;
	}

	public void setFwLoudong(String fwLoudong) {
		this.fwLoudong = fwLoudong;
	}

	public String getFwDanyuan() {
		return fwDanyuan;
	}

	public void setFwDanyuan(String fwDanyuan) {
		this.fwDanyuan = fwDanyuan;
	}

	public String getFwFanghao() {
		return fwFanghao;
	}

	public void setFwFanghao(String fwFanghao) {
		this.fwFanghao = fwFanghao;
	}

	public String getFwHuxing() {
		return fwHuxing;
	}

	public void setFwHuxing(String fwHuxing) {
		this.fwHuxing = fwHuxing;
	}

	public String getFwMainji() {
		return fwMainji;
	}

	public void setFwMainji(String fwMainji) {
		this.fwMainji = fwMainji;
	}

	public Integer getFwLoupanId() {
		return fwLoupanId;
	}

	public void setFwLoupanId(Integer fwLoupanId) {
		this.fwLoupanId = fwLoupanId;
	}

	public String getXiangmuLoupan() {
		return xiangmuLoupan;
	}

	public void setXiangmuLoupan(String xiangmuLoupan) {
		this.xiangmuLoupan = xiangmuLoupan;
	}

	public String getXiangmuTelphone() {
		return xiangmuTelphone;
	}

	public void setXiangmuTelphone(String xiangmuTelphone) {
		this.xiangmuTelphone = xiangmuTelphone;
	}

	public Integer getFwyzType() {
		return fwyzType;
	}

	public List<AppYezhuFangwu> getRows() {
		return rows;
	}

	public void setRows(List<AppYezhuFangwu> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "AppYezhuFangwu{" +
				"yezhuId=" + yezhuId +
				", fwId=" + fwId +
				", fwLoudong='" + fwLoudong + '\'' +
				", fwDanyuan='" + fwDanyuan + '\'' +
				", fwFanghao='" + fwFanghao + '\'' +
				", fwHuxing='" + fwHuxing + '\'' +
				", fwMainji='" + fwMainji + '\'' +
				", fwLoupanId=" + fwLoupanId +
				", fwyzType=" + fwyzType +
				", xiangmuLoupan='" + xiangmuLoupan + '\'' +
				", xiangmuTelphone='" + xiangmuTelphone + '\'' +
				'}';
	}

	public void setFwyzType(Integer fwyzType) {
		this.fwyzType = fwyzType;
	}

}
