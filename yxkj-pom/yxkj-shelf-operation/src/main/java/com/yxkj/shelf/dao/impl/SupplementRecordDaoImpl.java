package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementRecord;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.SupplementRecordDao;
@Repository("supplementRecordDaoImpl")
public class SupplementRecordDaoImpl extends  BaseDaoImpl<SupplementRecord,Long> implements SupplementRecordDao {

}