package com.yxkj.service.impl;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;

import com.yxkj.entity.CmdMsg;
import com.yxkj.entity.OrderItem;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.service.CmdService;
import org.springframework.stereotype.Service;

import com.yxkj.entity.Order;
import com.yxkj.dao.OrderDao;
import com.yxkj.service.OrderService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

    @Resource(name = "cmdServiceImpl")
    private CmdService cmdService;

    private OrderDao orderDao;

    @Resource(name = "orderDaoImpl")
    public void setBaseDao(OrderDao orderDao) {
        super.setBaseDao(orderDao);
        this.orderDao = orderDao;
    }

    @Override
    public void salesOut(Long orderId) {

//        List<OrderItem> orderItemList = orderDao.salesOut(order);
//
        List<CmdMsg> cmdMsgList = orderDao.salesOut(orderId);
        cmdMsgList.forEach(cmdMsg -> {
            cmdService.sendCmd(cmdMsg);
        });


    }
}