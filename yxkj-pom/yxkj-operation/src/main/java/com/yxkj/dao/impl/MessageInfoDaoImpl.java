package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MessageInfo;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.MessageInfoDao;
@Repository("messageInfoDaoImpl")
public class MessageInfoDaoImpl extends  BaseDaoImpl<MessageInfo,Long> implements MessageInfoDao {

}