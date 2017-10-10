package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.AdResource;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.AdResourceDao;
@Repository("adResourceDaoImpl")
public class AdResourceDaoImpl extends  BaseDaoImpl<AdResource,Long> implements AdResourceDao {

}