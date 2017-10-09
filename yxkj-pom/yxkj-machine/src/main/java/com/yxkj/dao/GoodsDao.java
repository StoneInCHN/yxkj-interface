package com.yxkj.dao;

import java.util.Map;

import com.yxkj.entity.Goods;
import com.yxkj.framework.dao.BaseDao;
import com.yxkj.json.base.ResponseMultiple;

public interface GoodsDao extends BaseDao<Goods, Long> {

  /**
   * 根据类别获取商品
   * 
   * @param cateId
   * @param cImei
   * @param pageSize
   * @param pageNum
   * @return
   */
  ResponseMultiple<Map<String, Object>> getGoodsByCate(Long cateId, String cImei, Integer pageSize,
      Integer pageNum);
}
