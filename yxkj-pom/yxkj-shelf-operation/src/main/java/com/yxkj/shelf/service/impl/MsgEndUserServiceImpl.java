package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MsgEndUser;
import com.yxkj.shelf.dao.MsgEndUserDao;
import com.yxkj.shelf.service.MsgEndUserService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("msgEndUserServiceImpl")
public class MsgEndUserServiceImpl extends BaseServiceImpl<MsgEndUser,Long> implements MsgEndUserService {

      @Resource(name="msgEndUserDaoImpl")
      public void setBaseDao(MsgEndUserDao msgEndUserDao) {
         super.setBaseDao(msgEndUserDao);
  }
}