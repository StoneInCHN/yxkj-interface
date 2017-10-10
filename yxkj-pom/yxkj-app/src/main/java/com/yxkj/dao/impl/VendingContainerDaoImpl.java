package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.VendingContainerDao;
@Repository("vendingContainerDaoImpl")
public class VendingContainerDaoImpl extends  BaseDaoImpl<VendingContainer,Long> implements VendingContainerDao {

}