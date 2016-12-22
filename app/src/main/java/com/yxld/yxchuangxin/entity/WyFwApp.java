package com.yxld.yxchuangxin.entity;

import com.yxld.yxchuangxin.base.BaseEntity;

import java.util.List;

/**
 * @author Carrol：App返回房屋信息
 * 2016年12月15日 11:55:27
 */
public class WyFwApp extends BaseEntity{

	private List<WyFwApp> house;

	public List<WyFwApp> getHouse() {
		return house;
	}

	public void setHouse(List<WyFwApp> house) {
		this.house = house;
	}

	private Object fwyzYezhu;//业主id
	private String fwId;//房屋id
	private Object fwLoudong;//楼栋
	private Object fwDanyuan;//单元
	private Object fwFanghao;//房号
	private Object fwHuxing;//户型
	private Object fwMianji;//面积
	private String fwJiaofangTime;//交房时间
	private Object fwLoupanId;//楼盘id
	private Object fwLoupanName;//楼盘名称
	private Object fwIsChuzu;//是否出租
	private Object fwZhuangtai;//房屋状态
	private String fwZhuangxiuTime;//装修时间
	private String fwShouFangTime;//交房时间
	private Object jfWyIsLateFees;//是否需缴纳滞纳金
	private Object jfWyTypeFwLeixing;//房屋类型id
	private Object jfWyTypeFeiyong;//每平米费用
	private Object jfWyTypeName;//缴费类型名称
	private Object jfWyTypeLateFees;//滞纳金比例
	private Object jfFwTypeLeixing;//房屋类型名称
	private String jfWyUseEndTime;//使用截止时间
	
	public WyFwApp() {
	}

	public WyFwApp(Object fwyzYezhu, String fwId, Object fwLoudong, Object fwDanyuan, Object fwFanghao, Object fwHuxing,
			Object fwMianji, String fwJiaofangTime, Object fwLoupanId, Object fwLoupanName, Object fwIsChuzu,
			Object fwZhuangtai, String fwZhuangxiuTime, String fwShouFangTime, Object jfWyIsLateFees,
			Object jfWyTypeFwLeixing, Object jfWyTypeFeiyong, Object jfWyTypeName, Object jfWyTypeLateFees,
			Object jfFwTypeLeixing, String jfWyUseEndTime) {
		this.fwyzYezhu = fwyzYezhu;
		this.fwId = fwId;
		this.fwLoudong = fwLoudong;
		this.fwDanyuan = fwDanyuan;
		this.fwFanghao = fwFanghao;
		this.fwHuxing = fwHuxing;
		this.fwMianji = fwMianji;
		this.fwJiaofangTime = fwJiaofangTime;
		this.fwLoupanId = fwLoupanId;
		this.fwLoupanName = fwLoupanName;
		this.fwIsChuzu = fwIsChuzu;
		this.fwZhuangtai = fwZhuangtai;
		this.fwZhuangxiuTime = fwZhuangxiuTime;
		this.fwShouFangTime = fwShouFangTime;
		this.jfWyIsLateFees = jfWyIsLateFees;
		this.jfWyTypeFwLeixing = jfWyTypeFwLeixing;
		this.jfWyTypeFeiyong = jfWyTypeFeiyong;
		this.jfWyTypeName = jfWyTypeName;
		this.jfWyTypeLateFees = jfWyTypeLateFees;
		this.jfFwTypeLeixing = jfFwTypeLeixing;
		this.jfWyUseEndTime = jfWyUseEndTime;
	}

	public Object getFwyzYezhu() {
		return fwyzYezhu;
	}
	public void setFwyzYezhu(Object fwyzYezhu) {
		this.fwyzYezhu = fwyzYezhu;
	}
	public String getFwId() {
		return fwId;
	}
	public void setFwId(String fwId) {
		this.fwId = fwId;
	}
	public Object getFwLoudong() {
		return fwLoudong;
	}
	public void setFwLoudong(Object fwLoudong) {
		this.fwLoudong = fwLoudong;
	}
	public Object getFwDanyuan() {
		return fwDanyuan;
	}
	public void setFwDanyuan(Object fwDanyuan) {
		this.fwDanyuan = fwDanyuan;
	}
	public Object getFwFanghao() {
		return fwFanghao;
	}
	public void setFwFanghao(Object fwFanghao) {
		this.fwFanghao = fwFanghao;
	}
	public Object getFwHuxing() {
		return fwHuxing;
	}
	public void setFwHuxing(Object fwHuxing) {
		this.fwHuxing = fwHuxing;
	}
	public Object getFwMianji() {
		return fwMianji;
	}
	public void setFwMianji(Object fwMianji) {
		this.fwMianji = fwMianji;
	}
	public String getFwJiaofangTime() {
		return fwJiaofangTime;
	}
	public void setFwJiaofangTime(String fwJiaofangTime) {
		this.fwJiaofangTime = fwJiaofangTime;
	}
	public Object getFwLoupanId() {
		return fwLoupanId;
	}
	public void setFwLoupanId(Object fwLoupanId) {
		this.fwLoupanId = fwLoupanId;
	}
	public Object getFwLoupanName() {
		return fwLoupanName;
	}
	public void setFwLoupanName(Object fwLoupanName) {
		this.fwLoupanName = fwLoupanName;
	}
	public Object getFwIsChuzu() {
		return fwIsChuzu;
	}
	public void setFwIsChuzu(Object fwIsChuzu) {
		this.fwIsChuzu = fwIsChuzu;
	}
	public Object getFwZhuangtai() {
		return fwZhuangtai;
	}
	public void setFwZhuangtai(Object fwZhuangtai) {
		this.fwZhuangtai = fwZhuangtai;
	}
	public String getFwZhuangxiuTime() {
		return fwZhuangxiuTime;
	}
	public void setFwZhuangxiuTime(String fwZhuangxiuTime) {
		this.fwZhuangxiuTime = fwZhuangxiuTime;
	}
	public String getFwShouFangTime() {
		return fwShouFangTime;
	}
	public void setFwShouFangTime(String fwShouFangTime) {
		this.fwShouFangTime = fwShouFangTime;
	}
	public Object getJfWyIsLateFees() {
		return jfWyIsLateFees;
	}
	public void setJfWyIsLateFees(Object jfWyIsLateFees) {
		this.jfWyIsLateFees = jfWyIsLateFees;
	}
	public Object getJfWyTypeFwLeixing() {
		return jfWyTypeFwLeixing;
	}
	public void setJfWyTypeFwLeixing(Object jfWyTypeFwLeixing) {
		this.jfWyTypeFwLeixing = jfWyTypeFwLeixing;
	}
	public Object getJfWyTypeFeiyong() {
		return jfWyTypeFeiyong;
	}
	public void setJfWyTypeFeiyong(Object jfWyTypeFeiyong) {
		this.jfWyTypeFeiyong = jfWyTypeFeiyong;
	}
	public Object getJfWyTypeName() {
		return jfWyTypeName;
	}
	public void setJfWyTypeName(Object jfWyTypeName) {
		this.jfWyTypeName = jfWyTypeName;
	}
	public Object getJfWyTypeLateFees() {
		return jfWyTypeLateFees;
	}
	public void setJfWyTypeLateFees(Object jfWyTypeLateFees) {
		this.jfWyTypeLateFees = jfWyTypeLateFees;
	}
	public Object getJfFwTypeLeixing() {
		return jfFwTypeLeixing;
	}
	public void setJfFwTypeLeixing(Object jfFwTypeLeixing) {
		this.jfFwTypeLeixing = jfFwTypeLeixing;
	}
	public String getJfWyUseEndTime() {
		return jfWyUseEndTime;
	}
	public void setJfWyUseEndTime(String jfWyUseEndTime) {
		this.jfWyUseEndTime = jfWyUseEndTime;
	}
	
}
