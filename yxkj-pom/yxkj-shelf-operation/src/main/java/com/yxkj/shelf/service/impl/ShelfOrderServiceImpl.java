package com.yxkj.shelf.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.entity.Company;
import com.yxkj.entity.Goods;
import com.yxkj.entity.ShelfOrder;
import com.yxkj.entity.ShelfOrderItem;
import com.yxkj.entity.Sn.Type;
import com.yxkj.entity.Tourist;
import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;
import com.yxkj.shelf.common.log.LogUtil;
import com.yxkj.shelf.dao.CompanyDao;
import com.yxkj.shelf.dao.ShelfOrderDao;
import com.yxkj.shelf.dao.SnDao;
import com.yxkj.shelf.dao.TouristDao;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;
import com.yxkj.shelf.json.beans.GoodsBean;
import com.yxkj.shelf.service.ShelfOrderService;

@Service("shelfOrderServiceImpl")
public class ShelfOrderServiceImpl extends BaseServiceImpl<ShelfOrder, Long> implements
    ShelfOrderService {

  @Resource(name = "shelfOrderDaoImpl")
  private ShelfOrderDao shelfOrderDao;

  @Resource(name = "companyDaoImpl")
  private CompanyDao companyDao;

  @Resource(name = "touristDaoImpl")
  private TouristDao touristDao;

  @Resource(name = "snDaoImpl")
  private SnDao snDao;

  @Resource(name = "shelfOrderDaoImpl")
  public void setBaseDao(ShelfOrderDao shelfOrderDao) {
    super.setBaseDao(shelfOrderDao);
  }

  @Override
  public ShelfOrder createShelfOrder(String payType, String userName, Long compId,
      List<GoodsBean> goodsBeans) {
    ShelfOrder shelfOrder = new ShelfOrder();
    shelfOrder.setPaymentType(payType);
    shelfOrder.setStatus(OrderStatus.UNPAID);
    Company company = companyDao.find(compId);
    shelfOrder.setComp(company);
    Tourist t = touristDao.getByUserId(userName);
    shelfOrder.setTourist(t);
    int totalCount = 0;
    BigDecimal profit = new BigDecimal(0);
    BigDecimal amount = new BigDecimal(0);
    for (GoodsBean goodsBean : goodsBeans) {
      Goods gs = goodsBean.getGoods();
      totalCount += goodsBean.getCount();
      ShelfOrderItem item = new ShelfOrderItem();
      item.setShelfOrder(shelfOrder);
      item.setgName(gs.getName());
      item.setSpec(gs.getSpec());
      item.setPrice(gs.getSalePrice());
      item.setCount(goodsBean.getCount());
      item.setgSn(gs.getSn());

      // 计算订单总价
      amount = amount.add(item.getTotalPrice());
      // 计算订单毛利
      BigDecimal itemProfit =
          (gs.getSalePrice().subtract(gs.getCostPrice())).multiply(new BigDecimal(item.getCount()));
      profit = profit.add(itemProfit);
      shelfOrder.getShelfOrderItems().add(item);
    }
    shelfOrder.setAmount(amount);
    shelfOrder.setItemCount(totalCount);
    shelfOrder.setProfit(profit);
    shelfOrder.setSn(snDao.generate(Type.ORDER));
    shelfOrderDao.persist(shelfOrder);
    return shelfOrder;
  }


  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public ShelfOrder callbackAfterPay(String orderSn) {
    ShelfOrder shelfOrder = shelfOrderDao.getShelfOrderBySn(orderSn);
    if (!OrderStatus.UNPAID.equals(shelfOrder.getStatus())) {
      LogUtil.debug(this.getClass(), "callbackAfterPay",
          "This order already deal with. orderSn: %s, orderStatus: %s", shelfOrder.getSn(),
          shelfOrder.getStatus().toString());
      return shelfOrder;
    }
    shelfOrder.setStatus(OrderStatus.PAID);
    shelfOrder.setPaymentTime(new Date());
    return shelfOrder;
  }

  @Override
  public ShelfOrder getShelfOrderBySn(String orderSn) {
    return shelfOrderDao.getShelfOrderBySn(orderSn);
  }
}
