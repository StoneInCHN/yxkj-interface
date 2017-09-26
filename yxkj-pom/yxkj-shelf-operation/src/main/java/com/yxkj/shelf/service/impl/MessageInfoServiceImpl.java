package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MessageInfo;
import com.yxkj.shelf.dao.MessageInfoDao;
import com.yxkj.shelf.service.MessageInfoService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("messageInfoServiceImpl")
public class MessageInfoServiceImpl extends BaseServiceImpl<MessageInfo,Long> implements MessageInfoService {

      @Resource(name="messageInfoDaoImpl")
      public void setBaseDao(MessageInfoDao messageInfoDao) {
         super.setBaseDao(messageInfoDao);
  }
}