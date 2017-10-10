package com.yxkj.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.GoodsDao;
import com.yxkj.entity.Goods;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.json.base.ResponseMultiple;

@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends BaseDaoImpl<Goods, Long> implements GoodsDao {

  @Override
  public ResponseMultiple<Map<String, Object>> getGoodsByCate(Long cateId, String cImei,
      Integer pageSize, Integer pageNum) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    StringBuffer sqlPage = new StringBuffer();
    sqlPage
        .append("select SQL_CALC_FOUND_ROWS goods.id,goods.name,goods.spec,goodsImage.source,channel.price,channel.chgs_status ");
    sqlPage
        .append("from t_goods goods,t_cntr_channel channel,t_vending_container vc,t_goods_image goodsImage");
    if (cateId != null) {
      sqlPage.append(",t_goods_category gCate");
    }
    sqlPage.append(" where 1=1 ");
    sqlPage.append("and vc.parent = (select v.id from t_vending_container v where v.sn=" + cImei
        + ") ");
    sqlPage.append("and vc.id=channel.cntr and goods.id=channel.goods ");
    sqlPage.append("and goods.id=goodsImage.goods and goodsImage.orders=1 ");
    if (cateId != null) {
      sqlPage.append("and gCate.id=goods.category ");
    }
    sqlPage.append("order by channel.sn asc");
    sqlPage.append(" limit ");
    sqlPage.append((pageNum - 1) * pageSize);
    sqlPage.append(",");
    sqlPage.append(pageSize);

    List list =
        entityManager.createNativeQuery(sqlPage.toString()).setFlushMode(FlushModeType.COMMIT)
            .getResultList();
    List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
    for (Object object : list) {
      Object[] arrays = (Object[]) object;
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("gId", arrays[0]);
      map.put("gName", arrays[1]);
      map.put("gSpec", arrays[2]);
      map.put("gImg", arrays[3]);
      map.put("price", arrays[4]);
      map.put("price", arrays[5]);
      resultMap.add(map);
    }


    response.setMsg(resultMap);
    // 总记录数
    BigInteger total =
        (BigInteger) entityManager.createNativeQuery("select FOUND_ROWS()")
            .setFlushMode(FlushModeType.COMMIT).getSingleResult();
    response.getPage().setTotal(total.intValue());
    response.getPage().setPageNumber(pageNum);
    response.getPage().setPageSize(pageSize);
    return response;
  }
}
