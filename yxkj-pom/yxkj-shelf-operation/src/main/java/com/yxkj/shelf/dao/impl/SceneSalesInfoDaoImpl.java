package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SceneSalesInfo;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.SceneSalesInfoDao;
@Repository("sceneSalesInfoDaoImpl")
public class SceneSalesInfoDaoImpl extends  BaseDaoImpl<SceneSalesInfo,Long> implements SceneSalesInfoDao {

}