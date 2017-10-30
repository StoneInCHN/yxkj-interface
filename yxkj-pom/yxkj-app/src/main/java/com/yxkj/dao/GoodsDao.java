package com.yxkj.dao; 
import java.util.List;

import com.yxkj.entity.Goods;
import com.yxkj.framework.dao.BaseDao;

public interface GoodsDao extends  BaseDao<Goods,Long>{

  List<Object[]> getContainerGoodsList(Long cntrId, int pageNo, int pageSize);
  
}