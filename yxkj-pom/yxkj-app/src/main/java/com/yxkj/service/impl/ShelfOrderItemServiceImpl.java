package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ShelfOrderItem;
import com.yxkj.dao.ShelfOrderItemDao;
import com.yxkj.service.ShelfOrderItemService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("shelfOrderItemServiceImpl")
public class ShelfOrderItemServiceImpl extends BaseServiceImpl<ShelfOrderItem,Long> implements ShelfOrderItemService {

      @Resource(name="shelfOrderItemDaoImpl")
      public void setBaseDao(ShelfOrderItemDao shelfOrderItemDao) {
         super.setBaseDao(shelfOrderItemDao);
  }
}