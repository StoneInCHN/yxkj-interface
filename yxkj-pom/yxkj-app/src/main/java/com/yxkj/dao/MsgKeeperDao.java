package com.yxkj.dao;

import java.util.List;

import com.yxkj.entity.MsgKeeper;
import com.yxkj.framework.dao.BaseDao;

public interface MsgKeeperDao extends  BaseDao<MsgKeeper,Long>{

  List<Object> findKeeperMsgType(Long userId);
  
  Integer countUnReadKeeperMsg(Long userId, String type);
  
  List<Object[]> getKeeperMsgByType(Long userId, String msgType);
  
  List<MsgKeeper> getMsgKeeperByType(Long userId, String msgType);
}