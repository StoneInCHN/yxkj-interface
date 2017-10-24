package com.yxkj.service.impl; 

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsPic;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
import com.yxkj.dao.GoodsDao;
import com.yxkj.service.GoodsService;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.bean.GoodsData;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("goodsServiceImpl")
public class GoodsServiceImpl extends BaseServiceImpl<Goods,Long> implements GoodsService {

      @Resource(name="goodsDaoImpl")
      public void setBaseDao(GoodsDao goodsDao) {
         super.setBaseDao(goodsDao);
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
    	  goods.setStatus(CommonStatus.ACITVE);
    	  if (goodsId == null) {
			  GoodsPic goodsPicSmall = new GoodsPic();
			  goodsPicSmall.setSource(goodsData.getSmallUrl());
			  goodsPicSmall.setTitle(goodsData.getName()+"(小图)");
			  goodsPicSmall.setOrder(0);
			  goods.getGoodsPics().add(goodsPicSmall);
			  
			  GoodsPic goodsPicLarge = new GoodsPic();
			  goodsPicLarge.setSource(goodsData.getLargeUrl());
			  goodsPicLarge.setTitle(goodsData.getName()+"(大图)");
			  goodsPicSmall.setOrder(1);
			  goods.getGoodsPics().add(goodsPicLarge);
    	  }else {
    		  List<GoodsPic> goodsPics = goods.getGoodsPics();
    		  for (GoodsPic goodsPic : goodsPics) {
				if (goodsPic.getOrder() == 0) {
					goodsPic.setSource(goodsData.getSmallUrl());
					goodsPic.setTitle(goodsData.getName()+"(小图)");
				}
				if (goodsPic.getOrder() == 1) {
					goodsPic.setSource(goodsData.getLargeUrl());
					goodsPic.setTitle(goodsData.getName()+"(大图)");
				}				
			 }
    	  }  
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
    		  for (GoodsPic goodsPic : goods.getGoodsPics()) {
  				if (goodsPic.getOrder() == 0) {
  					goodsData.setSmallUrl(goodsPic.getSource());
				}
				if (goodsPic.getOrder() == 1) {
					goodsData.setLargeUrl(goodsPic.getSource());
				}
			  }
    	  }
    	  return goodsData;
      }

      @Override
      public Goods findBySn(String sn) {
    	  List<Filter> filters = new ArrayList<Filter>();
    	  filters.add(Filter.eq("sn", sn));
    	  return findFirst(filters, null);
      }
}