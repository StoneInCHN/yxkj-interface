package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Area;
import com.yxkj.shelf.dao.AreaDao;
import com.yxkj.shelf.service.AreaService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("areaServiceImpl")
public class AreaServiceImpl extends BaseServiceImpl<Area,Long> implements AreaService {

      @Resource(name="areaDaoImpl")
      public void setBaseDao(AreaDao areaDao) {
         super.setBaseDao(areaDao);
  }
}