package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SupplementSumRecDao;
@Repository("supplementSumRecDaoImpl")
public class SupplementSumRecDaoImpl extends  BaseDaoImpl<SupplementSumRec,Long> implements SupplementSumRecDao {

}