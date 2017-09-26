package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementRecord;
import com.yxkj.shelf.dao.SupplementRecordDao;
import com.yxkj.shelf.service.SupplementRecordService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("supplementRecordServiceImpl")
public class SupplementRecordServiceImpl extends BaseServiceImpl<SupplementRecord,Long> implements SupplementRecordService {

      @Resource(name="supplementRecordDaoImpl")
      public void setBaseDao(SupplementRecordDao supplementRecordDao) {
         super.setBaseDao(supplementRecordDao);
  }
}