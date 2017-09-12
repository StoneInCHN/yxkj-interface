package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Area;
import com.yxkj.dao.AreaDao;
import com.yxkj.service.AreaService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("areaServiceImpl")
public class AreaServiceImpl extends BaseServiceImpl<Area,Long> implements AreaService {

      @Resource(name="areaDaoImpl")
      public void setBaseDao(AreaDao areaDao) {
         super.setBaseDao(areaDao);
  }
}