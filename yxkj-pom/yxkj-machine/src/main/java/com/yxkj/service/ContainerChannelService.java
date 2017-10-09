package com.yxkj.service;

import com.yxkj.entity.ContainerChannel;
import com.yxkj.framework.service.BaseService;

public interface ContainerChannelService extends BaseService<ContainerChannel, Long> {
  /**
   * 根据货道编号查询单个货道
   * 
   * @param cImei
   * @param channel
   * @return
   */
  ContainerChannel getByCImeiAndChannel(String cImei, String channel);
}
