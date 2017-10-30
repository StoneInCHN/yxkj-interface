package com.yxkj.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.GoodsSaleInfoDao;
import com.yxkj.entity.GoodsSaleInfo;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.GoodsSaleInfoService;

@Service("goodsSaleInfoServiceImpl")
public class GoodsSaleInfoServiceImpl extends BaseServiceImpl<GoodsSaleInfo, Long> implements
    GoodsSaleInfoService {

  @Resource(name = "goodsSaleInfoDaoImpl")
  private GoodsSaleInfoDao goodsSaleInfoDao;

  @Resource(name = "goodsSaleInfoDaoImpl")
  public void setBaseDao(GoodsSaleInfoDao goodsSaleInfoDao) {
    super.setBaseDao(goodsSaleInfoDao);
  }

  @Override
  public ResponseMultiple<Map<String, Object>> salesGoodsInfo(Date startTime, Date endTime,
      Long gCateId, Long sceneId, String gName, String gCode, Integer pageSize, Integer pageNum) {
    return goodsSaleInfoDao.salesGoodsInfo(startTime, endTime, gCateId, sceneId, gName, gCode,
        pageSize, pageNum);
  }
}
