package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.EndUser;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.EndUserDao;
@Repository("endUserDaoImpl")
public class EndUserDaoImpl extends  BaseDaoImpl<EndUser,Long> implements EndUserDao {

}