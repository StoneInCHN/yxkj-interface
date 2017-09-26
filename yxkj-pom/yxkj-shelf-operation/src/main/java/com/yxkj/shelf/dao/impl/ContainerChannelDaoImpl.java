package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerChannel;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.ContainerChannelDao;
@Repository("containerChannelDaoImpl")
public class ContainerChannelDaoImpl extends  BaseDaoImpl<ContainerChannel,Long> implements ContainerChannelDao {

}