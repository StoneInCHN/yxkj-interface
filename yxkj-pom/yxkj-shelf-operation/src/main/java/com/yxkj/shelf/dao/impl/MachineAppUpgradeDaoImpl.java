package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MachineAppUpgrade;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.MachineAppUpgradeDao;
@Repository("machineAppUpgradeDaoImpl")
public class MachineAppUpgradeDaoImpl extends  BaseDaoImpl<MachineAppUpgrade,Long> implements MachineAppUpgradeDao {

}