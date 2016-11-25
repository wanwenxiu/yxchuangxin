package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;

public class LoginEntity extends BaseEntity{
	public List<AppYezhuFangwu> house;
	
	public CxwyYezhu user;

	public String token;

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

	@Override
	public String toString() {
		return "LoginEntity{" +
				"house=" + house +
				", user=" + user +
				", token='" + token + '\'' +
				'}';
	}
}
