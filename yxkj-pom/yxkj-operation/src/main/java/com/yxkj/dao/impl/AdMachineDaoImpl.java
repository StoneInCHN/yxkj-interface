package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.AdMachine;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.AdMachineDao;
@Repository("adMachineDaoImpl")
public class AdMachineDaoImpl extends  BaseDaoImpl<AdMachine,Long> implements AdMachineDao {

}