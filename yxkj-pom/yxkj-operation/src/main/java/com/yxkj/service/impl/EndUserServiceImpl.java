package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.EndUser;
import com.yxkj.dao.EndUserDao;
import com.yxkj.service.EndUserService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("endUserServiceImpl")
public class EndUserServiceImpl extends BaseServiceImpl<EndUser,Long> implements EndUserService {

      @Resource(name="endUserDaoImpl")
      public void setBaseDao(EndUserDao endUserDao) {
         super.setBaseDao(endUserDao);
  }
}