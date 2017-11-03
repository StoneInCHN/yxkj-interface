package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.NoResultException;
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
        + " WHERE m.message = k AND m.keeper.id = :userId AND m.isPush = :isPush GROUP BY k.type HAVING m.isRead = :isRead";
    Query query = entityManager.createQuery(jpql).setParameter("userId", userId)
        .setParameter("isPush", true).setParameter("isRead", false);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> getKeeperMsgByType(Long userId, String msgType) {
    String jpql = "SELECT k.title, k.content, k.remark, k.createDate FROM KeeperRemindMsg k, MsgKeeper m WHERE"
        + " k.type = :msgType AND m.message = k AND m.keeper.id = :userId ORDER BY k.createDate DESC";
    Query query = entityManager.createQuery(jpql).setParameter("userId", userId)
        .setParameter("msgType", CommonEnum.RemindType.valueOf(msgType));
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<MsgKeeper> getMsgKeeperByType(Long userId, String msgType) {
    String jpql = "FROM MsgKeeper m WHERE m.message.type = :msgType AND m.keeper.id = :userId"
        + " AND m.isPush =:isPush AND m.isRead = :isRead";
    Query query = entityManager.createQuery(jpql).setParameter("userId", userId)
        .setParameter("msgType", CommonEnum.RemindType.valueOf(msgType))
        .setParameter("isPush", true).setParameter("isRead", false);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

}