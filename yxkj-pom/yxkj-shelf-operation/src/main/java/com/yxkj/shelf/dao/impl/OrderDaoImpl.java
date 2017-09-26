package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Order;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.OrderDao;
@Repository("orderDaoImpl")
public class OrderDaoImpl extends  BaseDaoImpl<Order,Long> implements OrderDao {

}