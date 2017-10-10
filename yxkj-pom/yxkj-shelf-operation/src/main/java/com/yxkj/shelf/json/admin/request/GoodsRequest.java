package com.yxkj.shelf.json.admin.request;

public class GoodsRequest extends AdminRequest{
	
	/**
	 * 商品条码
	 */
	private String sn;
	/**
	 * 商品名称
	 */
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
