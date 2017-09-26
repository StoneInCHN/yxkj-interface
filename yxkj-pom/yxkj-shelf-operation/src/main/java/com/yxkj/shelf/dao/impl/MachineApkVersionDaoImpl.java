package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MachineApkVersion;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.MachineApkVersionDao;
@Repository("machineApkVersionDaoImpl")
public class MachineApkVersionDaoImpl extends  BaseDaoImpl<MachineApkVersion,Long> implements MachineApkVersionDao {

}