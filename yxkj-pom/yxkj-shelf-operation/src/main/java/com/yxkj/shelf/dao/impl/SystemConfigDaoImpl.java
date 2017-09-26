package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SystemConfig;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.SystemConfigDao;
@Repository("systemConfigDaoImpl")
public class SystemConfigDaoImpl extends  BaseDaoImpl<SystemConfig,Long> implements SystemConfigDao {

}