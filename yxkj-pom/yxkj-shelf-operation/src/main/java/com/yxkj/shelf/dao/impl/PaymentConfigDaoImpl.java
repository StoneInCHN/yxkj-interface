package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.PaymentConfig;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.PaymentConfigDao;
@Repository("paymentConfigDaoImpl")
public class PaymentConfigDaoImpl extends  BaseDaoImpl<PaymentConfig,Long> implements PaymentConfigDao {

}