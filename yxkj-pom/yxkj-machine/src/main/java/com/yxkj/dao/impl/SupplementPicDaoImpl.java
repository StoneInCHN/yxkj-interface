package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementPic;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SupplementPicDao;
@Repository("supplementPicDaoImpl")
public class SupplementPicDaoImpl extends  BaseDaoImpl<SupplementPic,Long> implements SupplementPicDao {

}