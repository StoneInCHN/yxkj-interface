package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MenuAuthority;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.MenuAuthorityDao;
@Repository("menuAuthorityDaoImpl")
public class MenuAuthorityDaoImpl extends  BaseDaoImpl<MenuAuthority,Long> implements MenuAuthorityDao {

}