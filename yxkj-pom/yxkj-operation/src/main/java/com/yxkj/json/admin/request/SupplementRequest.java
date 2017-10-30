package com.yxkj.json.admin.request;

import com.yxkj.json.admin.bean.SupplementData;
import com.yxkj.json.base.BaseListRequest;

public class SupplementRequest extends BaseListRequest{
	
	private SupplementData supplementData;

	public SupplementData getSupplementData() {
		return supplementData;
	}

	public void setSupplementData(SupplementData supplementData) {
		this.supplementData = supplementData;
	}

}
