package com.yxkj.shelf.dao;

import com.yxkj.entity.Tourist;
import com.yxkj.shelf.framework.dao.BaseDao;

public interface TouristDao extends BaseDao<Tourist, Long> {

  /**
   * 根据游客唯一标识查询游客
   * 
   * @param userId
   * @return
   */
  Tourist getByUserId(String userId);
}
