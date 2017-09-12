package com.yxkj.dao.impl;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.AreaDao;
import com.yxkj.entity.Area;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

}
