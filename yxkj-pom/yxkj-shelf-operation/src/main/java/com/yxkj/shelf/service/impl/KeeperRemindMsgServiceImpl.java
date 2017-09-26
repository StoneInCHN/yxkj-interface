package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.KeeperRemindMsg;
import com.yxkj.shelf.dao.KeeperRemindMsgDao;
import com.yxkj.shelf.service.KeeperRemindMsgService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("keeperRemindMsgServiceImpl")
public class KeeperRemindMsgServiceImpl extends BaseServiceImpl<KeeperRemindMsg,Long> implements KeeperRemindMsgService {

      @Resource(name="keeperRemindMsgDaoImpl")
      public void setBaseDao(KeeperRemindMsgDao keeperRemindMsgDao) {
         super.setBaseDao(keeperRemindMsgDao);
  }
}