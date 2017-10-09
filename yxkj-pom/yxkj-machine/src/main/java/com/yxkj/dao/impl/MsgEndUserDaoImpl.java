package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MsgEndUser;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.MsgEndUserDao;
@Repository("msgEndUserDaoImpl")
public class MsgEndUserDaoImpl extends  BaseDaoImpl<MsgEndUser,Long> implements MsgEndUserDao {

}