package com.yxkj.shelf.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.yxkj.entity.Sn;
import com.yxkj.entity.Sn.Type;
import com.yxkj.shelf.dao.SnDao;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;
import com.yxkj.shelf.service.SnService;

@Service("snServiceImpl")
public class SnServiceImpl extends BaseServiceImpl<Sn, Long> implements SnService {

  @Resource(name = "snDaoImpl")
  private SnDao snDao;

  @Transactional
  public String generate(Type type) {
    return snDao.generate(type);
  }
}
