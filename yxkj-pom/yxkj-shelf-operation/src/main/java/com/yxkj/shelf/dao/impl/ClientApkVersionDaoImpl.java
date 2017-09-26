package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ClientApkVersion;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.ClientApkVersionDao;
@Repository("clientApkVersionDaoImpl")
public class ClientApkVersionDaoImpl extends  BaseDaoImpl<ClientApkVersion,Long> implements ClientApkVersionDao {

}