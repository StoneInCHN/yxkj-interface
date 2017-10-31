package com.yxkj.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.GoodsSaleInfoDao;
import com.yxkj.entity.GoodsSaleInfo;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.utils.TimeUtils;

@Repository("goodsSaleInfoDaoImpl")
public class GoodsSaleInfoDaoImpl extends BaseDaoImpl<GoodsSaleInfo, Long> implements
    GoodsSaleInfoDao {

  @Override
  public ResponseMultiple<Map<String, Object>> salesGoodsInfo(Date startTime, Date endTime,
      Long gCateId, Long sceneId, String gName, String gCode, Integer pageSize, Integer pageNum) {

    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();

    StringBuffer sqlPage = new StringBuffer();
    sqlPage
        .append("SELECT SQL_CALC_FOUND_ROWS tg.sn,tg.name,tg.spec,tg.sale_price,category.cate_name,toItem.salesCount,toItem.salesAmount ");
    sqlPage
        .append("FROM t_goods tg left join t_goods_category category on tg.category = category.id left join ");
    sqlPage
        .append("(select toi.g_sn,count(toi.id) as salesCount,sum(toi.price) as salesAmount from t_order_item toi,t_order o where toi.user_order=o.id and o.status!=0 ");
    if (sceneId != null) {
      sqlPage.append("and o.scene_id=");
      sqlPage.append(sceneId);
    }
    if (startTime != null) {
      sqlPage.append(" and toi.create_date>=");
      sqlPage.append("'");
      sqlPage.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
      sqlPage.append("'");
    }
    if (endTime != null) {
      sqlPage.append(" and toi.create_date<=");
      sqlPage.append("'");
      sqlPage.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
      sqlPage.append("'");
    }
    sqlPage.append(" group by toi.g_sn) as toItem ");
    sqlPage.append("on toItem.g_sn=tg.sn where 1=1 ");
    if (gCateId != null) {
      sqlPage.append("and tg.category=");
      sqlPage.append(gCateId);
    }
    if (gName != null) {
      sqlPage.append(" and tg.name like ");
      sqlPage.append("'%" + gName + "%'");
    }
    if (gCode != null) {
      sqlPage.append(" and tg.sn like ");
      sqlPage.append("'%" + gCode + "%'");
    }
    sqlPage.append(" order by salesAmount desc");
    if (pageNum != null && pageSize != null) {
      sqlPage.append(" limit ");
      sqlPage.append((pageNum - 1) * pageSize);
      sqlPage.append(",");
      sqlPage.append(pageSize);
    }


    List list =
        entityManager.createNativeQuery(sqlPage.toString()).setFlushMode(FlushModeType.COMMIT)
            .getResultList();
    List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
    for (Object object : list) {
      Object[] arrays = (Object[]) object;
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("gSn", arrays[0]);
      map.put("gName", arrays[1]);
      map.put("gSpec", arrays[2]);
      map.put("gPrice", arrays[3]);
      map.put("gCate", arrays[4]);
      map.put("salesCount", arrays[5]);
      map.put("salesAmount", arrays[6]);
      resultMap.add(map);
    }

    response.setMsg(resultMap);
    // 总记录数
    BigInteger total =
        (BigInteger) entityManager.createNativeQuery("select FOUND_ROWS()")
            .setFlushMode(FlushModeType.COMMIT).getSingleResult();
    PageResponse pageResponse = new PageResponse();
    pageResponse.setPageNumber(pageNum);
    pageResponse.setPageSize(pageSize);
    pageResponse.setTotal(total.intValue());
    response.setPage(pageResponse);
    return response;

  }

}
