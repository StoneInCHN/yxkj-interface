package com.yxkj.shelf.service;

import java.util.List;

import com.yxkj.entity.Goods;
import com.yxkj.shelf.framework.service.BaseService;
import com.yxkj.shelf.json.admin.response.GoodsProfile;

public interface GoodsService extends BaseService<Goods, Long> {

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
