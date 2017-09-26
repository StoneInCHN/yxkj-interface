package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.SupplementSumRecDao;
@Repository("supplementSumRecDaoImpl")
public class SupplementSumRecDaoImpl extends  BaseDaoImpl<SupplementSumRec,Long> implements SupplementSumRecDao {

}