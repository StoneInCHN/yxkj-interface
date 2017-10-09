package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MsgKeeper;
import com.yxkj.dao.MsgKeeperDao;
import com.yxkj.service.MsgKeeperService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("msgKeeperServiceImpl")
public class MsgKeeperServiceImpl extends BaseServiceImpl<MsgKeeper,Long> implements MsgKeeperService {

      @Resource(name="msgKeeperDaoImpl")
      public void setBaseDao(MsgKeeperDao msgKeeperDao) {
         super.setBaseDao(msgKeeperDao);
  }
}