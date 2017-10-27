package com.yxkj.service; 

import com.yxkj.entity.Goods;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.bean.GoodsData;

public interface GoodsService extends BaseService<Goods,Long>{

    Goods getGoodsEntity(GoodsData goodsData, Long goodsId);
    
    GoodsData getGoodsData(Goods goods);
    
    Goods findBySn(String sn);
}