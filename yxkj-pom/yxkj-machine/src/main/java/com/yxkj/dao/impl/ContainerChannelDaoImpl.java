package com.yxkj.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.ContainerChannelDao;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("containerChannelDaoImpl")
public class ContainerChannelDaoImpl extends BaseDaoImpl<ContainerChannel, Long> implements
    ContainerChannelDao {

  @Override
  public ContainerChannel getByCImeiAndChannel(String cImei, String channel) {
    if (cImei == null || channel == null) {
      return null;
    }
    try {
      String jpql =
          "select cc from ContainerChannel as cc left join cc.cntr as vc where cc.sn = :channel and vc.sn = :cSn and vc.parent.sn=:cImei";
      ContainerChannel cc =
          entityManager.createQuery(jpql, ContainerChannel.class)
              .setFlushMode(FlushModeType.COMMIT).setParameter("cImei", cImei)
              .setParameter("cSn", channel.subSequence(0, 1)).setParameter("channel", channel)
              .getSingleResult();
      return cc;
    } catch (NoResultException e) {
      return null;
    }
  }

}
