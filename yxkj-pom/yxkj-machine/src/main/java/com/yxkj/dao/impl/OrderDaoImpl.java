package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Order;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.OrderDao;
@Repository("orderDaoImpl")
public class OrderDaoImpl extends  BaseDaoImpl<Order,Long> implements OrderDao {

}