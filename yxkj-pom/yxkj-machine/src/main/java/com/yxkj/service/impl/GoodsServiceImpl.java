package com.yxkj.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.GoodsDao;
import com.yxkj.entity.Goods;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.GoodsService;

@Service("goodsServiceImpl")
public class GoodsServiceImpl extends BaseServiceImpl<Goods, Long> implements GoodsService {

  @Resource(name = "goodsDaoImpl")
  private GoodsDao goodsDao;

  @Resource(name = "goodsDaoImpl")
  public void setBaseDao(GoodsDao goodsDao) {
    super.setBaseDao(goodsDao);
  }

}
