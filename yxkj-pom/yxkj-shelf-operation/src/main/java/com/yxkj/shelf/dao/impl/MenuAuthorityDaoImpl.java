package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MenuAuthority;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.MenuAuthorityDao;
@Repository("menuAuthorityDaoImpl")
public class MenuAuthorityDaoImpl extends  BaseDaoImpl<MenuAuthority,Long> implements MenuAuthorityDao {

}