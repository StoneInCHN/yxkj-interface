package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.OperationMsgInfo;
import com.yxkj.dao.OperationMsgInfoDao;
import com.yxkj.service.OperationMsgInfoService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("operationMsgInfoServiceImpl")
public class OperationMsgInfoServiceImpl extends BaseServiceImpl<OperationMsgInfo,Long> implements OperationMsgInfoService {

      @Resource(name="operationMsgInfoDaoImpl")
      public void setBaseDao(OperationMsgInfoDao operationMsgInfoDao) {
         super.setBaseDao(operationMsgInfoDao);
  }
}