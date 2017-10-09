package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Admin;
import com.yxkj.dao.AdminDao;
import com.yxkj.service.AdminService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("adminServiceImpl")
public class AdminServiceImpl extends BaseServiceImpl<Admin,Long> implements AdminService {

      @Resource(name="adminDaoImpl")
      public void setBaseDao(AdminDao adminDao) {
         super.setBaseDao(adminDao);
  }
}