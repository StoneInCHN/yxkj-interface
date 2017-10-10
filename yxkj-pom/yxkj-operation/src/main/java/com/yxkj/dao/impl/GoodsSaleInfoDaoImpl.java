package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.GoodsSaleInfo;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.GoodsSaleInfoDao;
@Repository("goodsSaleInfoDaoImpl")
public class GoodsSaleInfoDaoImpl extends  BaseDaoImpl<GoodsSaleInfo,Long> implements GoodsSaleInfoDao {

}