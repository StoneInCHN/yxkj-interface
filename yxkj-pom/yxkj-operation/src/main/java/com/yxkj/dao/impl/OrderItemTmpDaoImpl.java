package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.OrderItemTmp;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.OrderItemTmpDao;
@Repository("orderItemTmpDaoImpl")
public class OrderItemTmpDaoImpl extends  BaseDaoImpl<OrderItemTmp,Long> implements OrderItemTmpDao {

}