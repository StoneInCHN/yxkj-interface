package com.yxkj.dao; 
import com.yxkj.entity.CmdMsg;
import com.yxkj.entity.Order;
import com.yxkj.entity.OrderItem;
import com.yxkj.framework.dao.BaseDao;

import java.util.List;

public interface OrderDao extends  BaseDao<Order,Long>{

    /**
     * 出货
     * @param order
     */
    List<CmdMsg> salesOut(Long orderId);
}