package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.OrderTmp;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.OrderTmpDao;
@Repository("orderTmpDaoImpl")
public class OrderTmpDaoImpl extends  BaseDaoImpl<OrderTmp,Long> implements OrderTmpDao {

}