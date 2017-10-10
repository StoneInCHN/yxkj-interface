package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.DictConfig;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.DictConfigDao;
@Repository("dictConfigDaoImpl")
public class DictConfigDaoImpl extends  BaseDaoImpl<DictConfig,Long> implements DictConfigDao {

}