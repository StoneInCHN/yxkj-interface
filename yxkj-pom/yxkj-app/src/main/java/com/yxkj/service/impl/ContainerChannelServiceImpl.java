package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ContainerChannel;
import com.yxkj.dao.ContainerChannelDao;
import com.yxkj.service.ContainerChannelService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("containerChannelServiceImpl")
public class ContainerChannelServiceImpl extends BaseServiceImpl<ContainerChannel,Long> implements ContainerChannelService {

      @Resource(name="containerChannelDaoImpl")
      public void setBaseDao(ContainerChannelDao containerChannelDao) {
         super.setBaseDao(containerChannelDao);
  }
}