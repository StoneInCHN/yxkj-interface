package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Admin;
import com.yxkj.shelf.dao.AdminDao;
import com.yxkj.shelf.service.AdminService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("adminServiceImpl")
public class AdminServiceImpl extends BaseServiceImpl<Admin,Long> implements AdminService {

      @Resource(name="adminDaoImpl")
      public void setBaseDao(AdminDao adminDao) {
         super.setBaseDao(adminDao);
  }
}