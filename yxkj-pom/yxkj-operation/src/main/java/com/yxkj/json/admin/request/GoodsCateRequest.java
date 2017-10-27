package com.yxkj.json.admin.request;

import com.yxkj.json.base.BaseRequest;


public class GoodsCateRequest extends BaseRequest{
		
	/**
	 * 商品类别名称
	*/
	private String cateName;
	
//	/**
//	 * 类别图片地址
//	 */
//	private String catePicUrl;

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

//	public String getCatePicUrl() {
//		return catePicUrl;
//	}
//
//	public void setCatePicUrl(String catePicUrl) {
//		this.catePicUrl = catePicUrl;
//	}

}
