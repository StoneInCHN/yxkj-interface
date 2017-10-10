package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementRecord;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SupplementRecordDao;
@Repository("supplementRecordDaoImpl")
public class SupplementRecordDaoImpl extends  BaseDaoImpl<SupplementRecord,Long> implements SupplementRecordDao {

}