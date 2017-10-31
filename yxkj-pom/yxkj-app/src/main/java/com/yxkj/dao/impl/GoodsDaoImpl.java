package com.yxkj.dao.impl; 

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Goods;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.GoodsDao;
@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends  BaseDaoImpl<Goods,Long> implements GoodsDao {

  @SuppressWarnings("unchecked")
  @Override
  public List<Object[]> getContainerGoodsList(Long cntrId, int pageNo, int pageSize) {
    String jpql = "SELECT c.id, c.sn, c.goods.sn, c.goods.name , c.surplus, (c.capacity-c.surplus)"
        + " FROM ContainerChannel c WHERE c.cntr.id = :cntrId";
    Query query = entityManager.createQuery(jpql).setParameter("cntrId", cntrId)
        .setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
    return query.getResultList();
  }

}