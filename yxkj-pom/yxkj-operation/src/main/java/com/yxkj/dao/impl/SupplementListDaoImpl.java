package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementList;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SupplementListDao;
@Repository("supplementListDaoImpl")
public class SupplementListDaoImpl extends  BaseDaoImpl<SupplementList,Long> implements SupplementListDao {

}