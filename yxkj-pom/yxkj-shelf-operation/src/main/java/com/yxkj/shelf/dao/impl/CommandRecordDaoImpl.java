package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.CommandRecord;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.CommandRecordDao;
@Repository("commandRecordDaoImpl")
public class CommandRecordDaoImpl extends  BaseDaoImpl<CommandRecord,Long> implements CommandRecordDao {

}