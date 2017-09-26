package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.MsgEndUser;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.MsgEndUserDao;
@Repository("msgEndUserDaoImpl")
public class MsgEndUserDaoImpl extends  BaseDaoImpl<MsgEndUser,Long> implements MsgEndUserDao {

}