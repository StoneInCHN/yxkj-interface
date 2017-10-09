package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Role;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.RoleDao;
@Repository("roleDaoImpl")
public class RoleDaoImpl extends  BaseDaoImpl<Role,Long> implements RoleDao {

}