package com.yxkj.shelf.dao;

import com.yxkj.entity.Sn;
import com.yxkj.entity.Sn.Type;
import com.yxkj.shelf.framework.dao.BaseDao;

public interface SnDao extends BaseDao<Sn, Long> {
  /**
   * 生成序列号
   * 
   * @param type 类型
   * @return 序列号
   */
  String generate(Type type);
}
