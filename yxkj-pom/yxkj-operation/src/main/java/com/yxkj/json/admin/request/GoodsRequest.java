package com.yxkj.json.admin.request;

import com.yxkj.json.admin.bean.GoodsData;
import com.yxkj.json.base.BaseRequest;

public class GoodsRequest extends BaseRequest{
	
	private GoodsData goodsData;
	
	public GoodsData getGoodsData() {
		return goodsData;
	}
	public void setGoodsData(GoodsData goodsData) {
		this.goodsData = goodsData;
	}

}
