package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.AdResource;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.AdResourceDao;
@Repository("adResourceDaoImpl")
public class AdResourceDaoImpl extends  BaseDaoImpl<AdResource,Long> implements AdResourceDao {

}