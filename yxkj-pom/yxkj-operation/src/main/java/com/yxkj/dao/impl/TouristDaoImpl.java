package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Tourist;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.TouristDao;
@Repository("touristDaoImpl")
public class TouristDaoImpl extends  BaseDaoImpl<Tourist,Long> implements TouristDao {

}