package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.VendingContainer;
import com.yxkj.shelf.dao.VendingContainerDao;
import com.yxkj.shelf.service.VendingContainerService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("vendingContainerServiceImpl")
public class VendingContainerServiceImpl extends BaseServiceImpl<VendingContainer,Long> implements VendingContainerService {

      @Resource(name="vendingContainerDaoImpl")
      public void setBaseDao(VendingContainerDao vendingContainerDao) {
         super.setBaseDao(vendingContainerDao);
  }
}