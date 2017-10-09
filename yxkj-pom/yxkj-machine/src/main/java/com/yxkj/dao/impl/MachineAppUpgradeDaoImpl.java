package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MachineAppUpgrade;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.MachineAppUpgradeDao;
@Repository("machineAppUpgradeDaoImpl")
public class MachineAppUpgradeDaoImpl extends  BaseDaoImpl<MachineAppUpgrade,Long> implements MachineAppUpgradeDao {

}