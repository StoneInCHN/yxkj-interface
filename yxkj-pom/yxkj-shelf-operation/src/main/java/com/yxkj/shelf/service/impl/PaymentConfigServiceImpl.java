package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.PaymentConfig;
import com.yxkj.shelf.dao.PaymentConfigDao;
import com.yxkj.shelf.service.PaymentConfigService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("paymentConfigServiceImpl")
public class PaymentConfigServiceImpl extends BaseServiceImpl<PaymentConfig,Long> implements PaymentConfigService {

      @Resource(name="paymentConfigDaoImpl")
      public void setBaseDao(PaymentConfigDao paymentConfigDao) {
         super.setBaseDao(paymentConfigDao);
  }
}