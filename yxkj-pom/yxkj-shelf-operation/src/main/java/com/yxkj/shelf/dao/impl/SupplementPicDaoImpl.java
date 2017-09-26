package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.SupplementPic;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.SupplementPicDao;
@Repository("supplementPicDaoImpl")
public class SupplementPicDaoImpl extends  BaseDaoImpl<SupplementPic,Long> implements SupplementPicDao {

}