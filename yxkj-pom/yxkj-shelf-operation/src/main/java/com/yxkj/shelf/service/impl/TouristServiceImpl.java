package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Tourist;
import com.yxkj.shelf.dao.TouristDao;
import com.yxkj.shelf.service.TouristService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("touristServiceImpl")
public class TouristServiceImpl extends BaseServiceImpl<Tourist,Long> implements TouristService {

      @Resource(name="touristDaoImpl")
      public void setBaseDao(TouristDao touristDao) {
         super.setBaseDao(touristDao);
  }
}