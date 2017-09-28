package com.yxkj.shelf.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.entity.Goods;
import com.yxkj.shelf.dao.GoodsDao;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;
import com.yxkj.shelf.service.GoodsService;

@Service("goodsServiceImpl")
public class GoodsServiceImpl extends BaseServiceImpl<Goods, Long> implements GoodsService {

  @Resource(name = "goodsDaoImpl")
  private GoodsDao goodsDao;

  @Resource(name = "goodsDaoImpl")
  public void setBaseDao(GoodsDao goodsDao) {
    super.setBaseDao(goodsDao);
  }

  @Override
  public Goods getBySn(String sn) {
    return goodsDao.getBySn(sn);
  }
}
