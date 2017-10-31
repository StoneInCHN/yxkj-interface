package com.yxkj.dao; 
import java.util.List;

import com.yxkj.entity.MsgKeeper;
import com.yxkj.framework.dao.BaseDao;

public interface MsgKeeperDao extends  BaseDao<MsgKeeper,Long>{

  List<Object[]> countKeeperMsgType(Long userId);
  
  List<Object[]> getKeeperMsgByType(Long userId, String msgType);
}