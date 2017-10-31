package com.yxkj.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.OrderDao;
import com.yxkj.entity.Order;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.utils.TimeUtils;

@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

  @Override
  public BigDecimal getPurTotal(Long sceneId, Date startTime, Date endTime) {
    try {
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT count(distinct tourist) FROM t_order where status!=0");
      if (sceneId != null) {
        sql.append(" and scene_id=");
        sql.append(sceneId);
      }
      if (startTime != null) {
        sql.append(" and create_date>=");
        sql.append("'");
        sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
        sql.append("'");
      }
      if (endTime != null) {
        sql.append(" and create_date<=");
        sql.append("'");
        sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
        sql.append("'");
      }
      BigDecimal total =
          new BigDecimal(entityManager.createNativeQuery(sql.toString())
              .setFlushMode(FlushModeType.COMMIT).getSingleResult().toString());
      return total;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public BigDecimal getPurRepeat(Integer count, Long sceneId, Date startTime, Date endTime) {
    try {
      StringBuffer sql = new StringBuffer();
      sql.append("select count(*) from (SELECT count(tourist) FROM t_order where status!=0");
      if (sceneId != null) {
        sql.append(" and scene_id=");
        sql.append(sceneId);
      }
      if (startTime != null) {
        sql.append(" and create_date>=");
        sql.append("'");
        sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
        sql.append("'");
      }
      if (endTime != null) {
        sql.append(" and create_date<=");
        sql.append("'");
        sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
        sql.append("'");
      }
      sql.append(" group by tourist having count(tourist)>=");
      sql.append(count);
      sql.append(") as t");
      BigDecimal total =
          new BigDecimal(entityManager.createNativeQuery(sql.toString())
              .setFlushMode(FlushModeType.COMMIT).getSingleResult().toString());
      return total;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public List<Map<String, Object>> salesGraphDataMap(Date startTime, Date endTime) {
    List<Map<String, Object>> salesMaps = new ArrayList<Map<String, Object>>();
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT date_format(o.create_date,'%Y-%c-%d') as report_date,sum(o.amount) as saleIncome,count(o.id) as orderCount, sum(o.amount-o.profit) as saleCost,format(sum(o.profit)/sum(o.amount),4) as profitRate, format(sum(o.amount)/count(o.id),2) as avgUnitPrice ");
    sql.append("FROM t_order o where o.status!=0 ");
    sql.append("and o.create_date>=");
    sql.append("'");
    sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
    sql.append("'");
    sql.append(" and o.create_date<=");
    sql.append("'");
    sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
    sql.append("'");
    sql.append(" group by date_format(create_date,'%Y-%c-%d') order by create_date asc");
    List list =
        entityManager.createNativeQuery(sql.toString()).setFlushMode(FlushModeType.COMMIT)
            .getResultList();
    for (Object object : list) {
      Object[] arrays = (Object[]) object;
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("reportDate", arrays[0]);
      map.put("saleIncome", arrays[1]);
      map.put("orderCount", arrays[2]);
      map.put("saleCost", arrays[3]);
      map.put("profitRate", arrays[4]);
      map.put("avgUnitPrice", arrays[5]);
      salesMaps.add(map);
    }
    return salesMaps;
  }

  @Override
  public List<Map<String, Object>> salesGraphUserCountMap(Date startTime, Date endTime) {
    List<Map<String, Object>> userCountMaps = new ArrayList<Map<String, Object>>();
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT date_format(create_date,'%Y-%c-%d') as reportDate,count(*) as userCount ");
    sql.append("FROM t_tourist where ");
    sql.append("create_date>=");
    sql.append("'");
    sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
    sql.append("'");
    sql.append(" and create_date<=");
    sql.append("'");
    sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
    sql.append("'");
    sql.append(" group by date_format(create_date,'%Y-%c-%d') order by create_date asc");
    List list =
        entityManager.createNativeQuery(sql.toString()).setFlushMode(FlushModeType.COMMIT)
            .getResultList();
    for (Object object : list) {
      Object[] arrays = (Object[]) object;
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("reportDate", arrays[0]);
      map.put("userCount", arrays[1]);
      userCountMaps.add(map);
    }
    return userCountMaps;
  }

  @Override
  public List<Map<String, Object>> salesGraphRepeatRateMap(Integer repeatCount, Date startTime,
      Date endTime) {
    List<Map<String, Object>> repeatCountMaps = new ArrayList<Map<String, Object>>();
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT date_format(create_date,'%Y-%c-%d'),count(distinct tourist) ");
    sql.append("FROM t_order where status!=0 ");
    sql.append(" and create_date>=");
    sql.append("'");
    sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
    sql.append("'");
    sql.append(" and create_date<=");
    sql.append("'");
    sql.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
    sql.append("'");
    sql.append(" group by date_format(create_date,'%Y-%c-%d') order by create_date asc");
    List totalUserList =
        entityManager.createNativeQuery(sql.toString()).setFlushMode(FlushModeType.COMMIT)
            .getResultList();

    StringBuffer sqlRp = new StringBuffer();
    sqlRp.append("select t.rDate,count(*) from ");
    sqlRp
        .append("(SELECT date_format(create_date,'%Y-%c-%d') as rDate,count(tourist) FROM t_order where status!=0 ");
    sqlRp.append("and create_date>=");
    sqlRp.append("'");
    sqlRp.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
    sqlRp.append("'");
    sqlRp.append(" and create_date<=");
    sqlRp.append("'");
    sqlRp.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
    sqlRp.append("'");
    sqlRp.append(" group by tourist,date_format(create_date,'%Y-%c-%d') having count(tourist)>=");
    sqlRp.append(repeatCount);
    sqlRp.append(" order by create_date asc) as t group by t.rDate");
    List rpUserList =
        entityManager.createNativeQuery(sqlRp.toString()).setFlushMode(FlushModeType.COMMIT)
            .getResultList();
    for (Object object : totalUserList) {
      Map<String, Object> map = new HashMap<String, Object>();
      Object[] arrays = (Object[]) object;
      for (Object o : rpUserList) {
        Object[] arr = (Object[]) o;
        if (arrays[0].toString().equals(arr[0].toString())) {// 相同日期
          map.put("reportDate", arrays[0]);
          map.put("repeatPurRate", new BigDecimal(arr[1].toString()).divide(new BigDecimal(
              arrays[1].toString()), 4, BigDecimal.ROUND_HALF_UP));
          repeatCountMaps.add(map);
          break;
        }
      }
    }
    return repeatCountMaps;
  }

  @Override
  public ResponseMultiple<Map<String, Object>> salesListDataMap(Long sceneId, Date startTime,
      Date endTime, Integer pageSize, Integer pageNum) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();

    StringBuffer sqlPage = new StringBuffer();
    sqlPage
        .append("SELECT SQL_CALC_FOUND_ROWS ts.id as tId,ts.sn as tSn,ts.name as tName,torder.saleIncome,torder.orderCount,torder.saleCost,torder.profitRate,torder.avgUnitPrice,tt.userCount ");
    sqlPage.append("from t_scene ts ");
    sqlPage
        .append(" left join (select scene_id,sum(o.amount) as saleIncome,count(o.id) as orderCount, sum(o.amount-o.profit) as saleCost,format(sum(o.profit)/sum(o.amount),4) as profitRate, format(sum(o.amount)/count(o.id),2) as avgUnitPrice from t_order o ");
    sqlPage.append("where o.status!=0");
    if (startTime != null) {
      sqlPage.append(" and o.create_date>=");
      sqlPage.append("'");
      sqlPage.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
      sqlPage.append("'");
    }
    if (endTime != null) {
      sqlPage.append(" and o.create_date<=");
      sqlPage.append("'");
      sqlPage.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
      sqlPage.append("'");
    }

    sqlPage.append(" group by scene_id) torder on torder.scene_id=ts.id ");
    sqlPage.append("left join (select scene_id,count(id) as userCount from t_tourist where 1=1");
    if (startTime != null) {
      sqlPage.append(" and create_date>=");
      sqlPage.append("'");
      sqlPage.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
      sqlPage.append("'");
    }
    if (endTime != null) {
      sqlPage.append(" and create_date<=");
      sqlPage.append("'");
      sqlPage.append(TimeUtils.format("yyyy-MM-dd HH:mm:ss", endTime.getTime()));
      sqlPage.append("'");
    }
    sqlPage.append(" group by scene_id) as tt on ts.id = tt.scene_id");
    sqlPage.append(" where 1=1");
    if (sceneId != null) {
      sqlPage.append(" and ts.id =");
      sqlPage.append(sceneId);
    }
    sqlPage.append(" order by torder.saleIncome desc");
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
      map.put("tId", arrays[0]);
      map.put("tSn", arrays[1]);
      map.put("tName", arrays[2]);
      map.put("saleIncome", arrays[3]);
      map.put("orderCount", arrays[4]);
      map.put("saleCost", arrays[5]);
      map.put("profitRate", arrays[6]);
      map.put("avgUnitPrice", arrays[7]);
      map.put("userCount", arrays[8]);
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
