package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MsgKeeper;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.MsgKeeperDao;
@Repository("msgKeeperDaoImpl")
public class MsgKeeperDaoImpl extends  BaseDaoImpl<MsgKeeper,Long> implements MsgKeeperDao {

}