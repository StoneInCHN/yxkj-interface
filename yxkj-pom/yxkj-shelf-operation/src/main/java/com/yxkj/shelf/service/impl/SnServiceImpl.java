package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Sn;
import com.yxkj.shelf.dao.SnDao;
import com.yxkj.shelf.service.SnService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("snServiceImpl")
public class SnServiceImpl extends BaseServiceImpl<Sn,Long> implements SnService {

      @Resource(name="snDaoImpl")
      public void setBaseDao(SnDao snDao) {
         super.setBaseDao(snDao);
  }
}