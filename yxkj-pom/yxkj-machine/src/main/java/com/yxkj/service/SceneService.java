package com.yxkj.service;

import com.yxkj.entity.Scene;
import com.yxkj.framework.service.BaseService;

public interface SceneService extends BaseService<Scene, Long> {
  /**
   * 根据IMEI查询货柜中控所属的优享空间
   * 
   * @param imei
   * @return
   */
  Scene getByImei(String imei);
}
