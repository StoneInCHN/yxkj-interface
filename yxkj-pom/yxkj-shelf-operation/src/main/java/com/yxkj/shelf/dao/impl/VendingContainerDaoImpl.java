package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.VendingContainer;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.VendingContainerDao;
@Repository("vendingContainerDaoImpl")
public class VendingContainerDaoImpl extends  BaseDaoImpl<VendingContainer,Long> implements VendingContainerDao {

}