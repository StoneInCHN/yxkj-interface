package com.yxkj.json.admin.request;

import com.yxkj.json.admin.bean.ContainerChannelData;
import com.yxkj.json.base.BaseRequest;

public class ContainerChannelRequest extends BaseRequest{		

	private ContainerChannelData channelData;

	public ContainerChannelData getChannelData() {
		return channelData;
	}

	public void setChannelData(ContainerChannelData channelData) {
		this.channelData = channelData;
	}

}
