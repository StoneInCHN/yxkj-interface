package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ShelfOrderItem;
import com.yxkj.shelf.dao.ShelfOrderItemDao;
import com.yxkj.shelf.service.ShelfOrderItemService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("shelfOrderItemServiceImpl")
public class ShelfOrderItemServiceImpl extends BaseServiceImpl<ShelfOrderItem,Long> implements ShelfOrderItemService {

      @Resource(name="shelfOrderItemDaoImpl")
      public void setBaseDao(ShelfOrderItemDao shelfOrderItemDao) {
         super.setBaseDao(shelfOrderItemDao);
  }
}