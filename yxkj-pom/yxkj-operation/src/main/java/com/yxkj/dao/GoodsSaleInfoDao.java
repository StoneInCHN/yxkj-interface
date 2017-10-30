package com.yxkj.dao;

import java.util.Date;
import java.util.Map;

import com.yxkj.entity.GoodsSaleInfo;
import com.yxkj.framework.dao.BaseDao;
import com.yxkj.json.base.ResponseMultiple;

public interface GoodsSaleInfoDao extends BaseDao<GoodsSaleInfo, Long> {
  /**
   * 商品收入统计
   * 
   * @param startTime
   * @param endTime
   * @param gCateId
   * @param sceneId
   * @param gName
   * @param gCode
   * @return
   */
  ResponseMultiple<Map<String, Object>> salesGoodsInfo(Date startTime, Date endTime, Long gCateId,
      Long sceneId, String gName, String gCode, Integer pageSize, Integer pageNum);
}
