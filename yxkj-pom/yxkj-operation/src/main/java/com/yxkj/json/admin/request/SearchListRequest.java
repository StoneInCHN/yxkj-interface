package com.yxkj.json.admin.request;

import com.yxkj.json.base.BaseListRequest;

public class SearchListRequest extends BaseListRequest{		
	/**
	 * 查询关键字
	*/
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
