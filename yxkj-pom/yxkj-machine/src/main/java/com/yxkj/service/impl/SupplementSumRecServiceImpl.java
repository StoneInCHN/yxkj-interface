package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.dao.SupplementSumRecDao;
import com.yxkj.service.SupplementSumRecService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("supplementSumRecServiceImpl")
public class SupplementSumRecServiceImpl extends BaseServiceImpl<SupplementSumRec,Long> implements SupplementSumRecService {

      @Resource(name="supplementSumRecDaoImpl")
      public void setBaseDao(SupplementSumRecDao supplementSumRecDao) {
         super.setBaseDao(supplementSumRecDao);
  }
}