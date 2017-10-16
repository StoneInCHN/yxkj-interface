package com.yxkj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.ContainerChannelDao;
import com.yxkj.dao.GoodsDao;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsPic;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.GoodsService;

@Service("goodsServiceImpl")
public class GoodsServiceImpl extends BaseServiceImpl<Goods, Long> implements GoodsService {

  @Resource(name = "goodsDaoImpl")
  private GoodsDao goodsDao;

  @Resource(name = "containerChannelDaoImpl")
  private ContainerChannelDao containerChannelDao;

  @Resource(name = "goodsDaoImpl")
  public void setBaseDao(GoodsDao goodsDao) {
    super.setBaseDao(goodsDao);
  }

  @Override
  public ResponseMultiple<Map<String, Object>> getGoodsByCate(Long cateId, String cImei,
      Integer pageSize, Integer pageNum) {
    return goodsDao.getGoodsByCate(cateId, cImei, pageSize, pageNum);
  }

  @Override
  public List<Map<String, Object>> getGforScanH5(String gList) {
    List<Map<String, Object>> gMaps = new ArrayList<Map<String, Object>>();
    String[] gl = gList.split(":");
    for (String gStr : gl) {
      Map<String, Object> gMap = new HashMap<String, Object>();
      String[] gc = gStr.split("-");
      ContainerChannel cc = containerChannelDao.find(Long.valueOf(gc[0]));
      Goods g = cc.getGoods();
      gMap.put("cSn", cc.getSn());
      gMap.put("cId", cc.getId());
      gMap.put("gName", g.getName());
      gMap.put("gSpec", g.getSpec());
      gMap.put("price", cc.getPrice());
      gMap.put("count", gc[1]);// 用户选购商品的数量
      String gImg = "";
      for (GoodsPic goodsPic : g.getGoodsPics()) {
        if (goodsPic.getOrder() != null && goodsPic.getOrder() == 0) {// 获取手机显示的小图
          gImg = goodsPic.getSource();
        }
      }
      gMap.put("gImg", gImg);
      gMaps.add(gMap);
    }
    return gMaps;
  }
}
