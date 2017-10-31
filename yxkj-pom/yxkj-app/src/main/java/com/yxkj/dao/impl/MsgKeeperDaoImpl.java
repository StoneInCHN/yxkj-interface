package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MsgKeeper;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.MsgKeeperDao;
@Repository("msgKeeperDaoImpl")
public class MsgKeeperDaoImpl extends  BaseDaoImpl<MsgKeeper,Long> implements MsgKeeperDao {

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> countKeeperMsgType(Long userId) {
    String jpql = "SELECT k.type, count(k.title) FROM MsgKeeper m, KeeperRemindMsg k"
        + " WHERE m.message = k AND m.keeper.id = :userId GROUP BY k.type";
    Query query = entityManager.createQuery(jpql).setParameter("userId", userId);
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> getKeeperMsgByType(Long userId, String msgType) {
    String jpql = "SELECT k.title, k.content, k.remark, k.sendDate FROM KeeperRemindMsg k, MsgKeeper m"
        + " WHERE m.message = k AND k.type = :msgType AND m.keeper.id = :userId ORDER BY k.sendDate ASC";
    Query query = entityManager.createQuery(jpql).setParameter("userId", userId)
        .setParameter("msgType", CommonEnum.RemindType.valueOf(msgType));
    return query.getResultList();
  }

}