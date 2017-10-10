package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MsgEndUser;
import com.yxkj.dao.MsgEndUserDao;
import com.yxkj.service.MsgEndUserService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("msgEndUserServiceImpl")
public class MsgEndUserServiceImpl extends BaseServiceImpl<MsgEndUser,Long> implements MsgEndUserService {

      @Resource(name="msgEndUserDaoImpl")
      public void setBaseDao(MsgEndUserDao msgEndUserDao) {
         super.setBaseDao(msgEndUserDao);
  }
}