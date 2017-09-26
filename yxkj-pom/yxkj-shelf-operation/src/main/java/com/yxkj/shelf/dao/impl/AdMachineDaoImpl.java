package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.AdMachine;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.AdMachineDao;
@Repository("adMachineDaoImpl")
public class AdMachineDaoImpl extends  BaseDaoImpl<AdMachine,Long> implements AdMachineDao {

}