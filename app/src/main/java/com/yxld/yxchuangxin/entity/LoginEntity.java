package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;

public class LoginEntity extends BaseEntity{
	public List<AppYezhuFangwu> house;
	
	public CxwyYezhu user;

	public String token;

	public String logintimeout;

	/** 版本实体 -- 判断是否存在新版本*/
	private CxwyAppVersion ver;

	public List<AppYezhuFangwu> getHouse() {
		return house;
	}

	public void setHouse(List<AppYezhuFangwu> house) {
		this.house = house;
	}

	public CxwyYezhu getUser() {
		return user;
	}

	public void setUser(CxwyYezhu user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public CxwyAppVersion getVer() {
		return ver;
	}

	public void setVer(CxwyAppVersion ver) {
		this.ver = ver;
	}

	public String getLogintimeout() {
		return logintimeout;
	}

	public void setLogintimeout(String logintimeout) {
		this.logintimeout = logintimeout;
	}

	@Override
	public String toString() {
		return "LoginEntity{" +
				"house=" + house +
				", user=" + user +
				", token='" + token + '\'' +
				", logintimeout='" + logintimeout + '\'' +
				", ver=" + ver +
				'}';
	}
}
