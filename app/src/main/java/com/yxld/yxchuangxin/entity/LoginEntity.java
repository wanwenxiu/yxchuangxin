package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;

public class LoginEntity extends BaseEntity{
	public List<CxwyYezhu> yzList;
	
	public CxwyMallUser user;

	public CxwyMallAdd defuleaddr;

	public CxwyMallAdd getDefuleaddr() {
		return defuleaddr;
	}

	public void setDefuleaddr(CxwyMallAdd defuleaddr) {
		this.defuleaddr = defuleaddr;
	}

	public List<CxwyYezhu> getYzList() {
		return yzList;
	}

	public void setYzList(List<CxwyYezhu> yzList) {
		this.yzList = yzList;
	}

	public CxwyMallUser getUser() {
		return user;
	}

	public void setUser(CxwyMallUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LoginEntity{" +
				"yzList=" + yzList +
				", user=" + user +
				", defuleaddr=" + defuleaddr +
				'}';
	}
}
