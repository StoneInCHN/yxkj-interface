package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.KeeperRemindMsg;
import com.yxkj.dao.KeeperRemindMsgDao;
import com.yxkj.service.KeeperRemindMsgService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("keeperRemindMsgServiceImpl")
public class KeeperRemindMsgServiceImpl extends BaseServiceImpl<KeeperRemindMsg,Long> implements KeeperRemindMsgService {

      @Resource(name="keeperRemindMsgDaoImpl")
      public void setBaseDao(KeeperRemindMsgDao keeperRemindMsgDao) {
         super.setBaseDao(keeperRemindMsgDao);
  }
}