package com.yxkj.shelf.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsPic;
import com.yxkj.shelf.dao.GoodsDao;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;
import com.yxkj.shelf.json.admin.request.GoodsData;
import com.yxkj.shelf.json.admin.response.GoodsProfile;
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

  @Override
  public List<GoodsProfile> getAllGoodsProfile() {
	return goodsDao.getAllGoodsProfile();
  }

  @Override
  public Goods getGoodsEntity(GoodsData goodsData, Long goodsId) {
	  Goods goods = null;
	  if (goodsId == null) {
		  goods = new Goods();
	  }else {
		  goods = find(goodsId);
	  }  
	  goods.setCostPrice(goodsData.getCostPrice());
	  goods.setSalePrice(goodsData.getSalePrice());
	  goods.setSn(goodsData.getSn());
	  goods.setName(goodsData.getName());
	  goods.setSpec(goodsData.getSpec());
	  GoodsPic goodsPic = new GoodsPic();
	  goodsPic.setSource(goodsData.getUrl());
	  goodsPic.setTitle(goodsData.getName());
	  goods.getGoodsPics().add(goodsPic);
	  return goods;
  }

  @Override
  public GoodsData getGoodsData(Goods goods) {
	  if (goods == null) {
		  return null;
	  }
	  GoodsData goodsData = new GoodsData();
	  goodsData.setCostPrice(goods.getCostPrice());
	  goodsData.setName(goods.getName());
	  goodsData.setSalePrice(goods.getSalePrice());
	  goodsData.setSn(goods.getSn());
	  goodsData.setSpec(goods.getSpec());
	  if (goods.getGoodsPics()!= null && goods.getGoodsPics().size() > 0) {
		  goodsData.setUrl(goods.getGoodsPics().get(0).getSource());
	  }
	  return goodsData;
  }
}
