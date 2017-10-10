package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SceneSalesInfo;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SceneSalesInfoDao;
@Repository("sceneSalesInfoDaoImpl")
public class SceneSalesInfoDaoImpl extends  BaseDaoImpl<SceneSalesInfo,Long> implements SceneSalesInfoDao {

}