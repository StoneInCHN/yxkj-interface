package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ContainerChannel;
import com.yxkj.shelf.dao.ContainerChannelDao;
import com.yxkj.shelf.service.ContainerChannelService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("containerChannelServiceImpl")
public class ContainerChannelServiceImpl extends BaseServiceImpl<ContainerChannel,Long> implements ContainerChannelService {

      @Resource(name="containerChannelDaoImpl")
      public void setBaseDao(ContainerChannelDao containerChannelDao) {
         super.setBaseDao(containerChannelDao);
  }
}