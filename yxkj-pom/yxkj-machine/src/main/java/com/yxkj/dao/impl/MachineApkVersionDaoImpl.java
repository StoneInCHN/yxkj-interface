package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MachineApkVersion;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.MachineApkVersionDao;
@Repository("machineApkVersionDaoImpl")
public class MachineApkVersionDaoImpl extends  BaseDaoImpl<MachineApkVersion,Long> implements MachineApkVersionDao {

}