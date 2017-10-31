package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.RefundHistory;
import com.yxkj.dao.RefundHistoryDao;
import com.yxkj.service.RefundHistoryService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("refundHistoryServiceImpl")
public class RefundHistoryServiceImpl extends BaseServiceImpl<RefundHistory,Long> implements RefundHistoryService {

      @Resource(name="refundHistoryDaoImpl")
      public void setBaseDao(RefundHistoryDao refundHistoryDao) {
         super.setBaseDao(refundHistoryDao);
  }
}