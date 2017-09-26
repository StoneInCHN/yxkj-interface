package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.AdBanner;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.AdBannerDao;
@Repository("adBannerDaoImpl")
public class AdBannerDaoImpl extends  BaseDaoImpl<AdBanner,Long> implements AdBannerDao {

}