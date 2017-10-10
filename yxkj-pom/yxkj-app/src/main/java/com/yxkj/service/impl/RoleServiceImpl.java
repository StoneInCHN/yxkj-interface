package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Role;
import com.yxkj.dao.RoleDao;
import com.yxkj.service.RoleService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

      @Resource(name="roleDaoImpl")
      public void setBaseDao(RoleDao roleDao) {
         super.setBaseDao(roleDao);
  }
}