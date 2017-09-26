package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.WarningConfig;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.WarningConfigDao;
@Repository("warningConfigDaoImpl")
public class WarningConfigDaoImpl extends  BaseDaoImpl<WarningConfig,Long> implements WarningConfigDao {

}