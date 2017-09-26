package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MsgKeeper;
import com.yxkj.shelf.dao.MsgKeeperDao;
import com.yxkj.shelf.service.MsgKeeperService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("msgKeeperServiceImpl")
public class MsgKeeperServiceImpl extends BaseServiceImpl<MsgKeeper,Long> implements MsgKeeperService {

      @Resource(name="msgKeeperDaoImpl")
      public void setBaseDao(MsgKeeperDao msgKeeperDao) {
         super.setBaseDao(msgKeeperDao);
  }
}