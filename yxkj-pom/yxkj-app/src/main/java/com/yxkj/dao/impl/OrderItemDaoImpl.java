package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.OrderItem;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.OrderItemDao;
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends  BaseDaoImpl<OrderItem,Long> implements OrderItemDao {

}