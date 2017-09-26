package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Admin;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.AdminDao;
@Repository("adminDaoImpl")
public class AdminDaoImpl extends  BaseDaoImpl<Admin,Long> implements AdminDao {

}