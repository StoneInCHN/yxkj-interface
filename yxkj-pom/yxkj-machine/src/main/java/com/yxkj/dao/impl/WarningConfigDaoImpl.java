package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.WarningConfig;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.WarningConfigDao;
@Repository("warningConfigDaoImpl")
public class WarningConfigDaoImpl extends  BaseDaoImpl<WarningConfig,Long> implements WarningConfigDao {

}