package com.yxkj.json.admin.request;

import com.yxkj.json.admin.bean.VendingContainerData;
import com.yxkj.json.base.BaseRequest;

public class VendingContainerRequest extends BaseRequest{		

	private VendingContainerData containerData;

	public VendingContainerData getContainerData() {
		return containerData;
	}

	public void setContainerData(VendingContainerData containerData) {
		this.containerData = containerData;
	}


}
