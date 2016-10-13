package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;

public class RepairLoupan extends BaseEntity{
	
	private List<CxwyXiangmu> rows;

	public List<CxwyXiangmu> getRows() {
		return rows;
	}

	public void setRows(List<CxwyXiangmu> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "CxwyXiangmu [rows=" + rows + ", status=" + status + ", MSG="
				+ MSG + "]";
	}
}
