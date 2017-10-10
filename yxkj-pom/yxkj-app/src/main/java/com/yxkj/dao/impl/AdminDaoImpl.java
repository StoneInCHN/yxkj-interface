package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Admin;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.AdminDao;
@Repository("adminDaoImpl")
public class AdminDaoImpl extends  BaseDaoImpl<Admin,Long> implements AdminDao {

}