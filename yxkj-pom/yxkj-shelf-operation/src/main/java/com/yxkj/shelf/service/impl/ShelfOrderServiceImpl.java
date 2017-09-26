package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ShelfOrder;
import com.yxkj.shelf.dao.ShelfOrderDao;
import com.yxkj.shelf.service.ShelfOrderService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("shelfOrderServiceImpl")
public class ShelfOrderServiceImpl extends BaseServiceImpl<ShelfOrder,Long> implements ShelfOrderService {

      @Resource(name="shelfOrderDaoImpl")
      public void setBaseDao(ShelfOrderDao shelfOrderDao) {
         super.setBaseDao(shelfOrderDao);
  }
}