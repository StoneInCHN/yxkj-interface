package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.KeeperRemindMsg;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.KeeperRemindMsgDao;
@Repository("keeperRemindMsgDaoImpl")
public class KeeperRemindMsgDaoImpl extends  BaseDaoImpl<KeeperRemindMsg,Long> implements KeeperRemindMsgDao {

}