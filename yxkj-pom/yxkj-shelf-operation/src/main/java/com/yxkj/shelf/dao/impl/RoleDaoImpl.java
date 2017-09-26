package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Role;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.RoleDao;
@Repository("roleDaoImpl")
public class RoleDaoImpl extends  BaseDaoImpl<Role,Long> implements RoleDao {

}