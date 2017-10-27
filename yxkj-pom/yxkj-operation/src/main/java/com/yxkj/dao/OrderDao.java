package com.yxkj.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yxkj.entity.Order;
import com.yxkj.framework.dao.BaseDao;
import com.yxkj.json.base.ResponseMultiple;

public interface OrderDao extends BaseDao<Order, Long> {

  /**
   * 查询总购买人数(不重复人数)
   * 
   * @return
   */
  BigDecimal getPurTotal(Long sceneId, Date startTime, Date endTime);

  /**
   * 根据复购次数查询重复购买人数
   * 
   * @return
   */
  BigDecimal getPurRepeat(Integer count, Long sceneId, Date startTime, Date endTime);

  /**
   * 根据日期获取数据概览折线图数据 (只获取总销售额,总订单数,总成本,毛利率,平均客单价; 总用户数和重复购买率单独接口获取)
   * 
   * @param startTime
   * @param endTime
   * @return
   */
  List<Map<String, Object>> salesGraphDataMap(Date startTime, Date endTime);


  /**
   * 根据日期获取数据概览折线图 总用户数 数据
   * 
   * @param startTime
   * @param endTime
   * @return
   */
  List<Map<String, Object>> salesGraphUserCountMap(Date startTime, Date endTime);

  /**
   * 根据日期获取数据概览折线图 重复购买率 数据
   * 
   * @param startTime
   * @param endTime
   * @return
   */
  List<Map<String, Object>> salesGraphRepeatRateMap(Integer repeatCount, Date startTime,
      Date endTime);

  /**
   * 获取优享空间销售数据列表
   * 
   * @param startTime
   * @param endTime
   * @return
   */
  ResponseMultiple<Map<String, Object>> salesListDataMap(Long sceneId, Date startTime,
      Date endTime, Integer pageSize, Integer pageNum);
}
