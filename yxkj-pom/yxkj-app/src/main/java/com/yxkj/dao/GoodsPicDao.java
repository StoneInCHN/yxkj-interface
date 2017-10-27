package com.yxkj.dao; 
import com.yxkj.entity.GoodsPic;
import com.yxkj.framework.dao.BaseDao;

public interface GoodsPicDao extends  BaseDao<GoodsPic,Long>{

  Object findGoodsPicByGoodsSn(String goodsSn);

}