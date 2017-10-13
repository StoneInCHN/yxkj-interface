package com.yxkj.dao.impl;

import com.yxkj.dao.CmdDao;
import com.yxkj.entity.CommandRecord;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Service;

@Service(value = "cmdDaoImpl")
public class CmdDaoImpl extends BaseDaoImpl<CommandRecord, Long> implements CmdDao {
}
