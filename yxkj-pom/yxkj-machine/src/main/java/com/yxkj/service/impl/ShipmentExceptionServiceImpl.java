package com.yxkj.service.impl;

import static com.yxkj.entity.commonenum.CommonEnum.ExcpReason.DEVICE_EXCEPTION;
import static com.yxkj.entity.commonenum.CommonEnum.ExcpReason.LACK_GOODS;
import static com.yxkj.entity.commonenum.CommonEnum.ShipmentExcpType.KEEPER_TEST;
import static com.yxkj.entity.commonenum.CommonEnum.ShipmentExcpType.USER_PICKUP;

import javax.annotation.Resource;

import com.yxkj.common.log.LogUtil;
import com.yxkj.service.VendingContainerService;
import org.springframework.stereotype.Service;

import com.yxkj.common.commonenum.CommonEnum;
import com.yxkj.dao.ShipmentExceptionDao;
import com.yxkj.entity.*;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.ContainerChannelService;
import com.yxkj.service.OrderItemService;
import com.yxkj.service.ShipmentExceptionService;

@Service("shipmentExceptionServiceImpl")
public class ShipmentExceptionServiceImpl extends BaseServiceImpl<ShipmentException, Long>
    implements ShipmentExceptionService {

  private ShipmentExceptionDao shipmentExceptionDao;

  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;

  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;
  @Resource(name = "orderItemServiceImpl")
  private OrderItemService orderItemService;

  @Resource(name = "shipmentExceptionDaoImpl")
  public void setBaseDao(ShipmentExceptionDao shipmentExceptionDao) {
    super.setBaseDao(shipmentExceptionDao);
    this.shipmentExceptionDao = shipmentExceptionDao;
  }

  @Override
  public void genShipmentException(CommandRecord record, String extMsg) {
    ShipmentException exception = new ShipmentException();
    // 关机出货测试异常
    if (record.getCmdType().equals(CommonEnum.CmdType.SELL_OUT_TEST)) {
      LogUtil.warn(ShipmentExceptionServiceImpl.class, "genShipmentException", "出货测试异常,货道Id:%s",
          record.getCmdContent());
      ContainerChannel containerChannel =
          containerChannelService.find(Long.valueOf(record.getCmdContent()));
      Goods goods = containerChannel.getGoods();
      VendingContainer vendingContainer = containerChannel.getCntr();
      VendingContainer machineContainer = vendingContainer.getParent();
      Scene scene = machineContainer.getScene();
      exception.setExcpType(KEEPER_TEST);
      exception.setChannelSn(containerChannel.getSn());
      exception.setCntrId(vendingContainer.getId());
      exception.setCntrSn(vendingContainer.getSn());
      exception.setSceneId(scene.getId());
      exception.setSceneName(scene.getName());
      exception.setgId(goods.getId());
      exception.setgName(goods.getName());
      exception.setgSn(goods.getSn());

      if (extMsg.equals(DEVICE_EXCEPTION.name())) {
        exception.setExcpReason(DEVICE_EXCEPTION);
      } else {
        exception.setExcpReason(LACK_GOODS);
      }
    } else if (record.getCmdType().equals(CommonEnum.CmdType.SELL_OUT)) {// 取货异常
      LogUtil.warn(ShipmentExceptionServiceImpl.class, "genShipmentException",
          "取货异常,orderItemId:%s", record.getCmdContent());
      OrderItem orderItem = orderItemService.find(Long.valueOf(record.getCmdContent()));
      VendingContainer vendingContainer = vendingContainerService.find(orderItem.getCntrId());
      VendingContainer machineContainer = vendingContainer.getParent();
      ContainerChannel containerChannel = containerChannelService
          .getByCImeiAndChannel(machineContainer.getSn(), orderItem.getChannelSn());

      Goods goods = containerChannel.getGoods();
      Scene scene = machineContainer.getScene();
      exception.setExcpType(USER_PICKUP);
      exception.setChannelSn(containerChannel.getSn());
      exception.setCntrId(vendingContainer.getId());
      exception.setCntrSn(vendingContainer.getSn());
      exception.setSceneId(scene.getId());
      exception.setSceneName(scene.getName());
      exception.setgId(goods.getId());
      exception.setgName(goods.getName());
      exception.setgSn(goods.getSn());
      if (extMsg.equals(DEVICE_EXCEPTION.name())) {
        exception.setExcpReason(DEVICE_EXCEPTION);
      } else {
        exception.setExcpReason(LACK_GOODS);
      }
    }
    shipmentExceptionDao.persist(exception);
  }
}
