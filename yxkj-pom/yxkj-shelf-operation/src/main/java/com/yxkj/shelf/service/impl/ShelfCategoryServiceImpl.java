package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ShelfCategory;
import com.yxkj.shelf.dao.ShelfCategoryDao;
import com.yxkj.shelf.service.ShelfCategoryService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("shelfCategoryServiceImpl")
public class ShelfCategoryServiceImpl extends BaseServiceImpl<ShelfCategory,Long> implements ShelfCategoryService {

      @Resource(name="shelfCategoryDaoImpl")
      public void setBaseDao(ShelfCategoryDao shelfCategoryDao) {
         super.setBaseDao(shelfCategoryDao);
  }
}