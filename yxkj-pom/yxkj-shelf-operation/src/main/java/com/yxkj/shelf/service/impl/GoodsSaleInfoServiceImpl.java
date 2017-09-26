package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.GoodsSaleInfo;
import com.yxkj.shelf.dao.GoodsSaleInfoDao;
import com.yxkj.shelf.service.GoodsSaleInfoService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("goodsSaleInfoServiceImpl")
public class GoodsSaleInfoServiceImpl extends BaseServiceImpl<GoodsSaleInfo,Long> implements GoodsSaleInfoService {

      @Resource(name="goodsSaleInfoDaoImpl")
      public void setBaseDao(GoodsSaleInfoDao goodsSaleInfoDao) {
         super.setBaseDao(goodsSaleInfoDao);
  }
}