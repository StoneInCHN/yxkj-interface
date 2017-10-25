package com.yxkj.json.admin.request;

import com.yxkj.json.admin.bean.GoodsData;
import com.yxkj.json.base.BaseListRequest;

public class GoodsListRequest extends BaseListRequest{
	
	private GoodsData goodsData;
	
	public GoodsData getGoodsData() {
		return goodsData;
	}
	public void setGoodsData(GoodsData goodsData) {
		this.goodsData = goodsData;
	}

}
