package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.PaymentConfig;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.PaymentConfigDao;
@Repository("paymentConfigDaoImpl")
public class PaymentConfigDaoImpl extends  BaseDaoImpl<PaymentConfig,Long> implements PaymentConfigDao {

}