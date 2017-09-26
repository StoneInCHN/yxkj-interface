package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.EndUser;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.EndUserDao;
@Repository("endUserDaoImpl")
public class EndUserDaoImpl extends  BaseDaoImpl<EndUser,Long> implements EndUserDao {

}