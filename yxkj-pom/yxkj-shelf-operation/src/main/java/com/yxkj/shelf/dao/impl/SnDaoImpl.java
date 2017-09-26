package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Sn;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.SnDao;
@Repository("snDaoImpl")
public class SnDaoImpl extends  BaseDaoImpl<Sn,Long> implements SnDao {

}