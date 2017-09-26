package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.KeeperRemindMsg;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.KeeperRemindMsgDao;
@Repository("keeperRemindMsgDaoImpl")
public class KeeperRemindMsgDaoImpl extends  BaseDaoImpl<KeeperRemindMsg,Long> implements KeeperRemindMsgDao {

}