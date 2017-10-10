package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MenuAuthority;
import com.yxkj.dao.MenuAuthorityDao;
import com.yxkj.service.MenuAuthorityService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("menuAuthorityServiceImpl")
public class MenuAuthorityServiceImpl extends BaseServiceImpl<MenuAuthority,Long> implements MenuAuthorityService {

      @Resource(name="menuAuthorityDaoImpl")
      public void setBaseDao(MenuAuthorityDao menuAuthorityDao) {
         super.setBaseDao(menuAuthorityDao);
  }
}