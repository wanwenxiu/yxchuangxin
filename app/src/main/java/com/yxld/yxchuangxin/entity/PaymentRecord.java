package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;

public class PaymentRecord extends BaseEntity {
	public List<CxwyWyjiaofeijl> rows;

	public List<CxwyWyjiaofeijl> getRows() {
		return rows;
	}

	public void setRows(List<CxwyWyjiaofeijl> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PaymentRecord [rows=" + rows + ", status=" + status + ", MSG="
				+ MSG + "]";
	}
}
