package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.AdBanner;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.AdBannerDao;
@Repository("adBannerDaoImpl")
public class AdBannerDaoImpl extends  BaseDaoImpl<AdBanner,Long> implements AdBannerDao {

}