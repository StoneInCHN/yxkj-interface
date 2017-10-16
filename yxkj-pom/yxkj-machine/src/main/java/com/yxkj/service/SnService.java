package com.yxkj.service;

import com.yxkj.entity.Sn;
import com.yxkj.entity.Sn.Type;
import com.yxkj.framework.service.BaseService;

public interface SnService extends BaseService<Sn, Long> {
  /**
   * 生成序列号
   * 
   * @param type 类型
   * @return 序列号
   */
  String generate(Type type);
}
