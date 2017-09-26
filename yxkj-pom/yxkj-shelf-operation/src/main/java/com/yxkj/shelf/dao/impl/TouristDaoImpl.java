package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Tourist;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.TouristDao;
@Repository("touristDaoImpl")
public class TouristDaoImpl extends  BaseDaoImpl<Tourist,Long> implements TouristDao {

}