package com.yxld.yxchuangxin.entity;

import java.sql.Timestamp;

/**
 * @ClassName: AppYezhuFangwu 
 * @Description:  App业主房屋中间表
 * @author wwx
 * @date 2016-11-24 下午6:09:20 
 */
public class AppYezhuFangwu {

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
	/** 房屋交房时间*/
	private String fwJiaofangTime;
	/** 楼盘ID*/
	private Integer fwLoupanId;
	/** 房屋状态*/
	private Integer fwFangwuStatus;
	/** 是否出租*/
	private Integer fwIsChuzu;
	/** 楼房状态*/
	private String fwLoufangStatus;
	/** 房屋状态*/
	private Integer fwZhuangtai;
	/** 装修时间*/
	private String fwZhuangxiuTime;
	
	/** 房屋楼盘名称*/
	private String xiangmuLoupan; //楼盘
	/** 小区电话*/
	private String xiangmuTelphone;//电话号码
	/** 纬度*/
	private  String xiangmuLatitude; //纬度
	/** 经度*/
    private String  xiangmuLongitude; //经度

	public AppYezhuFangwu(Integer yezhuId, Integer fwId, String fwLoudong, String fwDanyuan, String fwFanghao, String fwHuxing, String fwMainji, String fwJiaofangTime, Integer fwLoupanId, Integer fwFangwuStatus, Integer fwIsChuzu, String fwLoufangStatus, Integer fwZhuangtai, String fwZhuangxiuTime, String xiangmuLoupan, String xiangmuTelphone, String xiangmuLatitude, String xiangmuLongitude) {
		this.yezhuId = yezhuId;
		this.fwId = fwId;
		this.fwLoudong = fwLoudong;
		this.fwDanyuan = fwDanyuan;
		this.fwFanghao = fwFanghao;
		this.fwHuxing = fwHuxing;
		this.fwMainji = fwMainji;
		this.fwJiaofangTime = fwJiaofangTime;
		this.fwLoupanId = fwLoupanId;
		this.fwFangwuStatus = fwFangwuStatus;
		this.fwIsChuzu = fwIsChuzu;
		this.fwLoufangStatus = fwLoufangStatus;
		this.fwZhuangtai = fwZhuangtai;
		this.fwZhuangxiuTime = fwZhuangxiuTime;
		this.xiangmuLoupan = xiangmuLoupan;
		this.xiangmuTelphone = xiangmuTelphone;
		this.xiangmuLatitude = xiangmuLatitude;
		this.xiangmuLongitude = xiangmuLongitude;
	}



	public AppYezhuFangwu(){}


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
	public String getFwJiaofangTime() {
		return fwJiaofangTime;
	}
	public void setFwJiaofangTime(String fwJiaofangTime) {
		this.fwJiaofangTime = fwJiaofangTime;
	}
	public Integer getFwLoupanId() {
		return fwLoupanId;
	}
	public void setFwLoupanId(Integer fwLoupanId) {
		this.fwLoupanId = fwLoupanId;
	}
	public Integer getFwFangwuStatus() {
		return fwFangwuStatus;
	}
	public void setFwFangwuStatus(Integer fwFangwuStatus) {
		this.fwFangwuStatus = fwFangwuStatus;
	}
	public Integer getFwIsChuzu() {
		return fwIsChuzu;
	}
	public void setFwIsChuzu(Integer fwIsChuzu) {
		this.fwIsChuzu = fwIsChuzu;
	}
	public String getFwLoufangStatus() {
		return fwLoufangStatus;
	}
	public void setFwLoufangStatus(String fwLoufangStatus) {
		this.fwLoufangStatus = fwLoufangStatus;
	}
	public Integer getFwZhuangtai() {
		return fwZhuangtai;
	}
	public void setFwZhuangtai(Integer fwZhuangtai) {
		this.fwZhuangtai = fwZhuangtai;
	}
	public String getFwZhuangxiuTime() {
		return fwZhuangxiuTime;
	}
	public void setFwZhuangxiuTime(String fwZhuangxiuTime) {
		this.fwZhuangxiuTime = fwZhuangxiuTime;
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
		return "AppYezhuFangwu [yezhuId=" + yezhuId + ", fwId=" + fwId
				+ ", fwLoudong=" + fwLoudong + ", fwDanyuan=" + fwDanyuan
				+ ", fwFanghao=" + fwFanghao + ", fwHuxing=" + fwHuxing
				+ ", fwMainji=" + fwMainji + ", fwJiaofangTime="
				+ fwJiaofangTime + ", fwLoupanId=" + fwLoupanId
				+ ", fwFangwuStatus=" + fwFangwuStatus + ", fwIsChuzu="
				+ fwIsChuzu + ", fwLoufangStatus=" + fwLoufangStatus
				+ ", fwZhuangtai=" + fwZhuangtai + ", fwZhuangxiuTime="
				+ fwZhuangxiuTime + ", xiangmuLoupan=" + xiangmuLoupan
				+ ", xiangmuTelphone=" + xiangmuTelphone + ", xiangmuLatitude="
				+ xiangmuLatitude + ", xiangmuLongitude=" + xiangmuLongitude
				+ "]";
	}
    
    
}
