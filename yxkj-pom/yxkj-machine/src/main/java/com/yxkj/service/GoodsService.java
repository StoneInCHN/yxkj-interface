package com.yxkj.service;

import java.util.List;
import java.util.Map;

import com.yxkj.entity.Goods;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.base.ResponseMultiple;

public interface GoodsService extends BaseService<Goods, Long> {

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


  /**
   * 扫码H5页面获取商品列表
   * 
   * @param gList
   * @return
   */
  List<Map<String, Object>> getGforScanH5(String gList);

}
