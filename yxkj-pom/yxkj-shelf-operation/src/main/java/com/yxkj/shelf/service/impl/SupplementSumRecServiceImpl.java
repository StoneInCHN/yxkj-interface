package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.shelf.dao.SupplementSumRecDao;
import com.yxkj.shelf.service.SupplementSumRecService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("supplementSumRecServiceImpl")
public class SupplementSumRecServiceImpl extends BaseServiceImpl<SupplementSumRec,Long> implements SupplementSumRecService {

      @Resource(name="supplementSumRecDaoImpl")
      public void setBaseDao(SupplementSumRecDao supplementSumRecDao) {
         super.setBaseDao(supplementSumRecDao);
  }
}