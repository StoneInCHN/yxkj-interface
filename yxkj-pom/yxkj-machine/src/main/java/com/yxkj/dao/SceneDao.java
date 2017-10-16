package com.yxkj.dao;

import com.yxkj.entity.Scene;
import com.yxkj.framework.dao.BaseDao;

public interface SceneDao extends BaseDao<Scene, Long> {
  /**
   * 根据IMEI查询货柜中控所属的优享空间
   * 
   * @param imei
   * @return
   */
  Scene getByImei(String imei);
}
