package com.yxkj.json.admin.request;



public class GoodsCateRequest {
	/**
	 * ID
	 */
	private Long id;
		
	/**
	 * 商品类别名称
	*/
	private String cateName;
	
	/**
	 * 类别图片地址
	 */
	private String catePicUrl;
	
	/** 用户名 */
	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCatePicUrl() {
		return catePicUrl;
	}

	public void setCatePicUrl(String catePicUrl) {
		this.catePicUrl = catePicUrl;
	}

}
