package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.GoodsSaleInfo;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.GoodsSaleInfoDao;
@Repository("goodsSaleInfoDaoImpl")
public class GoodsSaleInfoDaoImpl extends  BaseDaoImpl<GoodsSaleInfo,Long> implements GoodsSaleInfoDao {

}