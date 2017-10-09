package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.GoodsPic;
import com.yxkj.dao.GoodsPicDao;
import com.yxkj.service.GoodsPicService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("goodsPicServiceImpl")
public class GoodsPicServiceImpl extends BaseServiceImpl<GoodsPic,Long> implements GoodsPicService {

      @Resource(name="goodsPicDaoImpl")
      public void setBaseDao(GoodsPicDao goodsPicDao) {
         super.setBaseDao(goodsPicDao);
  }
}