package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ShelfCategory;
import com.yxkj.dao.ShelfCategoryDao;
import com.yxkj.service.ShelfCategoryService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("shelfCategoryServiceImpl")
public class ShelfCategoryServiceImpl extends BaseServiceImpl<ShelfCategory,Long> implements ShelfCategoryService {

      @Resource(name="shelfCategoryDaoImpl")
      public void setBaseDao(ShelfCategoryDao shelfCategoryDao) {
         super.setBaseDao(shelfCategoryDao);
  }
}