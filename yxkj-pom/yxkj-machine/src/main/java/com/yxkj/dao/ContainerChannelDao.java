package com.yxkj.dao;

import com.yxkj.entity.ContainerChannel;
import com.yxkj.framework.dao.BaseDao;

public interface ContainerChannelDao extends BaseDao<ContainerChannel, Long> {
  /**
   * 根据货道编号查询单个货道
   * 
   * @param cImei
   * @param channel
   * @return
   */
  ContainerChannel getByCImeiAndChannel(String cImei, String channel);
}
