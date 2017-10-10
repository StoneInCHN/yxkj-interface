package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementList;
import com.yxkj.dao.SupplementListDao;
import com.yxkj.service.SupplementListService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("supplementListServiceImpl")
public class SupplementListServiceImpl extends BaseServiceImpl<SupplementList,Long> implements SupplementListService {

      @Resource(name="supplementListDaoImpl")
      public void setBaseDao(SupplementListDao supplementListDao) {
         super.setBaseDao(supplementListDao);
  }
}