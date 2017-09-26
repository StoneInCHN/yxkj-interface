package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MessageInfo;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.MessageInfoDao;
@Repository("messageInfoDaoImpl")
public class MessageInfoDaoImpl extends  BaseDaoImpl<MessageInfo,Long> implements MessageInfoDao {

}