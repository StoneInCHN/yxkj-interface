package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementList;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.SupplementListDao;
@Repository("supplementListDaoImpl")
public class SupplementListDaoImpl extends  BaseDaoImpl<SupplementList,Long> implements SupplementListDao {

}