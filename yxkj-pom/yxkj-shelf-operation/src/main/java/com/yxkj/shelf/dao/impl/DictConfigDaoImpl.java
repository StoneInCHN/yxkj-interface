package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.DictConfig;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.DictConfigDao;
@Repository("dictConfigDaoImpl")
public class DictConfigDaoImpl extends  BaseDaoImpl<DictConfig,Long> implements DictConfigDao {

}