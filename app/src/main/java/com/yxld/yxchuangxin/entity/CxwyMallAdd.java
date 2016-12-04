package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;

/**
 * CxwyMallAdd entity. @author MyEclipse Persistence Tools
 */

public class CxwyMallAdd extends BaseEntity implements java.io.Serializable {
	
	private List<CxwyMallAdd> addList;

	// Fields
	private Integer addId;
	private String addName;//联系人
	private String addVillage="";//小区
	private String addTel;//电话
	private String addAdd="";//详细地址
	private String addUserName;//用户名
	private Integer addStatus;//状态位，设置为默认地址,0为默认地址，1不位默认地址
	private String addSpare1 ="";//省市区
	private String addSpare2;
	private String addSpare3;
	private String addSpare4;

	// Constructors

	/** default constructor */
	public CxwyMallAdd() {
	}

	/** full constructor */
	public CxwyMallAdd(String addName, String addVillage, String addTel,
			String addAdd, String addUserName, Integer addStatus,
			String addSpare1, String addSpare2, String addSpare3,
			String addSpare4) {
		this.addName = addName;
		this.addVillage = addVillage;
		this.addTel = addTel;
		this.addAdd = addAdd;
		this.addUserName = addUserName;
		this.addStatus = addStatus;
		this.addSpare1 = addSpare1;
		this.addSpare2 = addSpare2;
		this.addSpare3 = addSpare3;
		this.addSpare4 = addSpare4;
	}

	// Property accessors

	public Integer getAddId() {
		return this.addId;
	}

	public void setAddId(Integer addId) {
		this.addId = addId;
	}

	public String getAddName() {
		return this.addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	public String getAddVillage() {
		return this.addVillage;
	}

	public void setAddVillage(String addVillage) {
		this.addVillage = addVillage;
	}

	public String getAddTel() {
		return this.addTel;
	}

	public void setAddTel(String addTel) {
		this.addTel = addTel;
	}

	public String getAddAdd() {
		return this.addAdd;
	}

	public void setAddAdd(String addAdd) {
		this.addAdd = addAdd;
	}

	public String getAddUserName() {
		return this.addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public Integer getAddStatus() {
		return this.addStatus;
	}

	public void setAddStatus(Integer addStatus) {
		this.addStatus = addStatus;
	}

	public String getAddSpare1() {
		return this.addSpare1;
	}

	public void setAddSpare1(String addSpare1) {
		this.addSpare1 = addSpare1;
	}

	public String getAddSpare2() {
		return this.addSpare2;
	}

	public void setAddSpare2(String addSpare2) {
		this.addSpare2 = addSpare2;
	}

	public String getAddSpare3() {
		return this.addSpare3;
	}

	public void setAddSpare3(String addSpare3) {
		this.addSpare3 = addSpare3;
	}

	public String getAddSpare4() {
		return this.addSpare4;
	}

	public void setAddSpare4(String addSpare4) {
		this.addSpare4 = addSpare4;
	}
	
	public List<CxwyMallAdd> getAddList() {
		return addList;
	}

	public void setAddList(List<CxwyMallAdd> addList) {
		this.addList = addList;
	}

	@Override
	public String toString() {
		return "CxwyMallAdd [addList=" + addList + ", addId=" + addId
				+ ", addName=" + addName + ", addVillage=" + addVillage
				+ ", addTel=" + addTel + ", addAdd=" + addAdd
				+ ", addUserName=" + addUserName + ", addStatus=" + addStatus
				+ ", addSpare1=" + addSpare1 + ", addSpare2=" + addSpare2
				+ ", addSpare3=" + addSpare3 + ", addSpare4=" + addSpare4
				+ ", status=" + status + ", MSG=" + MSG + "]";
	}
}