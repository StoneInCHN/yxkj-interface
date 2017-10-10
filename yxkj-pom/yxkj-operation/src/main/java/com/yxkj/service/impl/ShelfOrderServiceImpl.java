package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ShelfOrder;
import com.yxkj.dao.ShelfOrderDao;
import com.yxkj.service.ShelfOrderService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("shelfOrderServiceImpl")
public class ShelfOrderServiceImpl extends BaseServiceImpl<ShelfOrder,Long> implements ShelfOrderService {

      @Resource(name="shelfOrderDaoImpl")
      public void setBaseDao(ShelfOrderDao shelfOrderDao) {
         super.setBaseDao(shelfOrderDao);
  }
}