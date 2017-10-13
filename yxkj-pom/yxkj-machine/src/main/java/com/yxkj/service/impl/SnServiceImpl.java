package com.yxkj.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.SnDao;
import com.yxkj.entity.Sn;
import com.yxkj.entity.Sn.Type;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.SnService;

@Service("snServiceImpl")
public class SnServiceImpl extends BaseServiceImpl<Sn, Long> implements SnService {

  @Resource(name = "snDaoImpl")
  private SnDao snDao;

  @Resource(name = "snDaoImpl")
  public void setBaseDao(SnDao snDao) {
    super.setBaseDao(snDao);
  }

  @Override
  public String generate(Type type) {
    return snDao.generate(type);
  }
}
