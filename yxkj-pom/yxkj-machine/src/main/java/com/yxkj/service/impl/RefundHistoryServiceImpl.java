package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.dao.RefundHistoryDao;
import com.yxkj.entity.RefundHistory;
import com.yxkj.service.RefundHistoryService;
import org.springframework.stereotype.Service;

import com.yxkj.dao.CompanyDao;
import com.yxkj.entity.Company;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.CompanyService;

@Service("refundHistoryServiceImpl")
public class RefundHistoryServiceImpl extends BaseServiceImpl<RefundHistory, Long>
    implements RefundHistoryService {

  @Resource(name = "refundHistoryDaoImpl")
  public void setBaseDao(RefundHistoryDao refundHistoryDao) {
    super.setBaseDao(refundHistoryDao);
  }
}
