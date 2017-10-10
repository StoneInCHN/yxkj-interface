package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerChannel;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ContainerChannelDao;
@Repository("containerChannelDaoImpl")
public class ContainerChannelDaoImpl extends  BaseDaoImpl<ContainerChannel,Long> implements ContainerChannelDao {

}