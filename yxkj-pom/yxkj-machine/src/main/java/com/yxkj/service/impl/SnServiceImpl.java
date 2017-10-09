package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Sn;
import com.yxkj.dao.SnDao;
import com.yxkj.service.SnService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("snServiceImpl")
public class SnServiceImpl extends BaseServiceImpl<Sn,Long> implements SnService {

      @Resource(name="snDaoImpl")
      public void setBaseDao(SnDao snDao) {
         super.setBaseDao(snDao);
  }
}