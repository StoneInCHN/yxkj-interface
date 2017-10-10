package com.yxkj.shelf.dao;

import java.util.List;

import com.yxkj.entity.Goods;
import com.yxkj.shelf.framework.dao.BaseDao;
import com.yxkj.shelf.json.admin.response.GoodsProfile;

public interface GoodsDao extends BaseDao<Goods, Long> {
  /**
   * 根据条码查询商品
   * 
   * @param sn
   * @return
   */
  Goods getBySn(String sn);
  /**
   * 获取所有商品简介
   * @return
   */
  List<GoodsProfile> getAllGoodsProfile();
}
