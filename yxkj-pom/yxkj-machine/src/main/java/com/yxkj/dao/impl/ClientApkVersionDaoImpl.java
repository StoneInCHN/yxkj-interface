package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ClientApkVersion;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ClientApkVersionDao;
@Repository("clientApkVersionDaoImpl")
public class ClientApkVersionDaoImpl extends  BaseDaoImpl<ClientApkVersion,Long> implements ClientApkVersionDao {

}