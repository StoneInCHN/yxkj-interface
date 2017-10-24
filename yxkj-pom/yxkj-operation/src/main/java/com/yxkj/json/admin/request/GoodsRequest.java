package com.yxkj.json.admin.request;

import com.yxkj.json.base.BaseListRequest;

public class GoodsRequest extends BaseListRequest{
	
	/** 商品编号 */
	private String sn;
	
	/** 商品名称 */
	private String name;	
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
