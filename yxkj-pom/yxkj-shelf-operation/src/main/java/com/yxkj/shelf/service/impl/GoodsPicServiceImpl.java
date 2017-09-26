package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.GoodsPic;
import com.yxkj.shelf.dao.GoodsPicDao;
import com.yxkj.shelf.service.GoodsPicService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("goodsPicServiceImpl")
public class GoodsPicServiceImpl extends BaseServiceImpl<GoodsPic,Long> implements GoodsPicService {

      @Resource(name="goodsPicDaoImpl")
      public void setBaseDao(GoodsPicDao goodsPicDao) {
         super.setBaseDao(goodsPicDao);
  }
}