package com.yxkj.dao; 
import java.util.List;

import com.yxkj.entity.ContainerChannel;
import com.yxkj.framework.dao.BaseDao;

public interface ContainerChannelDao extends  BaseDao<ContainerChannel,Long>{

  List<Object[]> getContainerGoodsList(Long cntrId, int pageNo, int pageSize);

  List<Object[]> getChannelSupplyInfo();
  
}