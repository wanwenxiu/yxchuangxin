package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;

public class ShopList extends BaseEntity {
	
	private int total;

	public List<CxwyMallProduct> productList;

	public List<CxwyMallProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<CxwyMallProduct> productList) {
		this.productList = productList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ShopList [total=" + total + ", productList=" + productList
				+ ", status=" + status + ", MSG=" + MSG + "]";
	}
	
	

}
