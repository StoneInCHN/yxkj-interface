package com.yxkj.shelf.dao.impl;

import org.springframework.stereotype.Repository;

import com.yxkj.shelf.dao.AreaDao;
import com.yxkj.entity.Area;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;

@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

}
