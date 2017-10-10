package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.GoodsSaleInfo;
import com.yxkj.dao.GoodsSaleInfoDao;
import com.yxkj.service.GoodsSaleInfoService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("goodsSaleInfoServiceImpl")
public class GoodsSaleInfoServiceImpl extends BaseServiceImpl<GoodsSaleInfo,Long> implements GoodsSaleInfoService {

      @Resource(name="goodsSaleInfoDaoImpl")
      public void setBaseDao(GoodsSaleInfoDao goodsSaleInfoDao) {
         super.setBaseDao(goodsSaleInfoDao);
  }
}