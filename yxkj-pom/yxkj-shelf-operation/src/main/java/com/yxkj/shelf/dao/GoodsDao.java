package com.yxkj.shelf.dao;

import com.yxkj.entity.Goods;
import com.yxkj.shelf.framework.dao.BaseDao;

public interface GoodsDao extends BaseDao<Goods, Long> {
  /**
   * 根据条码查询商品
   * 
   * @param sn
   * @return
   */
  Goods getBySn(String sn);
}
