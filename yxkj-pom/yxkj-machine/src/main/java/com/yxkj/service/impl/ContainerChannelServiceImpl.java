package com.yxkj.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.ContainerChannelDao;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.ContainerChannelService;

@Service("containerChannelServiceImpl")
public class ContainerChannelServiceImpl extends BaseServiceImpl<ContainerChannel, Long> implements
    ContainerChannelService {

  @Resource(name = "containerChannelDaoImpl")
  private ContainerChannelDao containerChannelDao;

  @Resource(name = "containerChannelDaoImpl")
  public void setBaseDao(ContainerChannelDao containerChannelDao) {
    super.setBaseDao(containerChannelDao);
  }

  @Override
  public ContainerChannel getByCImeiAndChannel(String cImei, String channel) {
    return containerChannelDao.getByCImeiAndChannel(cImei, channel);
  }
}
