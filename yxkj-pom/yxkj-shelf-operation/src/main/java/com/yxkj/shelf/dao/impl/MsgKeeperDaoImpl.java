package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MsgKeeper;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.MsgKeeperDao;
@Repository("msgKeeperDaoImpl")
public class MsgKeeperDaoImpl extends  BaseDaoImpl<MsgKeeper,Long> implements MsgKeeperDao {

}