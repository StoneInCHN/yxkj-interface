package com.yxkj.service;

import java.util.List;
import java.util.Map;

import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.beans.GoodsBean;

public interface ContainerChannelService extends BaseService<ContainerChannel, Long> {
  /**
   * 根据货道编号查询单个货道
   * 
   * @param cImei
   * @param channel
   * @return
   */
  ContainerChannel getByCImeiAndChannel(String cImei, String channel);

  /**
   * 验证所选商品的库存数量
   * 
   * @param gList
   * @return
   */
  ResponseMultiple<Map<String, Object>> verifyStock(List<String> gList);


  /**
   * 验证所选商品的所有商品都有库存
   *
   * @param gList
   * @return
   */
  Boolean isVerifyStockSuccess(List<GoodsBean> gList, CommonEnum.PurMethod purMethod);
}
