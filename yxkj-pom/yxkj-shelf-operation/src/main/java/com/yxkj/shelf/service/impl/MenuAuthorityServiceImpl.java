package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MenuAuthority;
import com.yxkj.shelf.dao.MenuAuthorityDao;
import com.yxkj.shelf.service.MenuAuthorityService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("menuAuthorityServiceImpl")
public class MenuAuthorityServiceImpl extends BaseServiceImpl<MenuAuthority,Long> implements MenuAuthorityService {

      @Resource(name="menuAuthorityDaoImpl")
      public void setBaseDao(MenuAuthorityDao menuAuthorityDao) {
         super.setBaseDao(menuAuthorityDao);
  }
}