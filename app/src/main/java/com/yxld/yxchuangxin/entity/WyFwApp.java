package com.yxld.yxchuangxin.entity;

import com.yxld.yxchuangxin.base.BaseEntity;

import java.util.List;

/**
 * @author Carrol：App返回房屋信息
 * 2016年12月15日 11:55:27
 */
public class WyFwApp extends BaseEntity{

	private List<WyFwApp> house;
	private Object fwyzYezhu;//业主id
	private String fwId;//房屋id
	private String fwAddr;//地址
	private String fwJiaofangTime;//交房时间
	private Object fwLoupanName;//楼盘名称
	private Object fwZhuangtai;//房屋状态
	private Object fwShouFangTime;//收房时间
	
	private Object jfWyIsLateFees;//是否需缴纳滞纳金
	private Object jfWyTypeFwLeixing;//房屋类型id
	private String jfWyTypeFeiyong;//每平米费用
	private Object jfWyTypeName;//缴费类型名称
	private Object jfWyTypeLateFees;//滞纳金比例
	private Object jfFwTypeLeixing;//房屋类型名称
	private String jfWyUseEndTime;//使用截止时间
	
	private String arrearages;//欠费金额
	private String arrearLateFees;//欠费滞纳金额


	
	public WyFwApp() {
	}
	public List<WyFwApp> getHouse() {
		return house;
	}

	public void setHouse(List<WyFwApp> house) {
		this.house = house;
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
	public String getFwAddr() {
		return fwAddr;
	}
	public void setFwAddr(String fwAddr) {
		this.fwAddr = fwAddr;
	}
	public String getFwJiaofangTime() {
		return fwJiaofangTime;
	}
	public void setFwJiaofangTime(String fwJiaofangTime) {
		this.fwJiaofangTime = fwJiaofangTime;
	}
	public Object getFwLoupanName() {
		return fwLoupanName;
	}
	public void setFwLoupanName(Object fwLoupanName) {
		this.fwLoupanName = fwLoupanName;
	}
	public Object getFwZhuangtai() {
		return fwZhuangtai;
	}
	public void setFwZhuangtai(Object fwZhuangtai) {
		this.fwZhuangtai = fwZhuangtai;
	}
	public Object getFwShouFangTime() {
		return fwShouFangTime;
	}
	public void setFwShouFangTime(Object fwShouFangTime) {
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
	public String getJfWyTypeFeiyong() {
		return jfWyTypeFeiyong;
	}
	public void setJfWyTypeFeiyong(String jfWyTypeFeiyong) {
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
	public String getArrearages() {
		return arrearages;
	}
	public void setArrearages(String arrearages) {
		this.arrearages = arrearages;
	}
	public String getArrearLateFees() {
		return arrearLateFees;
	}
	public void setArrearLateFees(String arrearLateFees) {
		this.arrearLateFees = arrearLateFees;
	}
	
}
