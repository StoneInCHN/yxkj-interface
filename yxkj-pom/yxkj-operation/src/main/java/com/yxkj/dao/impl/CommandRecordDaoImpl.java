package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.CommandRecord;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.CommandRecordDao;
@Repository("commandRecordDaoImpl")
public class CommandRecordDaoImpl extends  BaseDaoImpl<CommandRecord,Long> implements CommandRecordDao {

}