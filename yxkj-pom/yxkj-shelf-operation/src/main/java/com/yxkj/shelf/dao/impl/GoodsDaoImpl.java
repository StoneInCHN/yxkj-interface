package com.yxkj.shelf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.yxkj.entity.Goods;
import com.yxkj.shelf.dao.GoodsDao;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.json.admin.response.GoodsProfile;

@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends BaseDaoImpl<Goods, Long> implements GoodsDao {

  @Override
  public Goods getBySn(String sn) {
    if (sn == null) {
      return null;
    }
    try {
      String jpql = "select g from Goods g where g.sn = :sn";
      return entityManager.createQuery(jpql, Goods.class).setFlushMode(FlushModeType.COMMIT)
          .setParameter("sn", sn).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public List<GoodsProfile> getAllGoodsProfile() {
	  List<GoodsProfile> goodsProfiles = new ArrayList<GoodsProfile>();
		String sql = "select id,name,sn,spec,status from t_goods order by sn";
		@SuppressWarnings("rawtypes")
		List cityReportlist = entityManager.createNativeQuery(sql).setFlushMode(FlushModeType.COMMIT).getResultList();
	    for (Object object : cityReportlist) {
	    	Object[] entity = (Object[]) object;
	    	GoodsProfile goodsProfile = new GoodsProfile(entity[0].toString(), 
	    			entity[1].toString(), entity[2].toString(), entity[3].toString(), 
	    			Integer.parseInt(entity[4].toString()) == 0? false:true);
	    	goodsProfiles.add(goodsProfile);
	    }
	return goodsProfiles;
  }

}
