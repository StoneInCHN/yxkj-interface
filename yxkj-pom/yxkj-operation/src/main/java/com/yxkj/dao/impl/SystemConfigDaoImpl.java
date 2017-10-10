package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SystemConfig;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SystemConfigDao;
@Repository("systemConfigDaoImpl")
public class SystemConfigDaoImpl extends  BaseDaoImpl<SystemConfig,Long> implements SystemConfigDao {

}