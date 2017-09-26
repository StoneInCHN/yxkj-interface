package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.GoodsCategory;
import com.yxkj.shelf.dao.GoodsCategoryDao;
import com.yxkj.shelf.service.GoodsCategoryService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("goodsCategoryServiceImpl")
public class GoodsCategoryServiceImpl extends BaseServiceImpl<GoodsCategory,Long> implements GoodsCategoryService {

      @Resource(name="goodsCategoryDaoImpl")
      public void setBaseDao(GoodsCategoryDao goodsCategoryDao) {
         super.setBaseDao(goodsCategoryDao);
  }
}