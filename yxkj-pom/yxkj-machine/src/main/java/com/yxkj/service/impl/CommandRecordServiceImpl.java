package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.common.commonenum.CommonEnum;
import com.yxkj.service.OrderItemService;
import com.yxkj.service.ShipmentExceptionService;
import org.springframework.stereotype.Service;

import com.yxkj.entity.CommandRecord;
import com.yxkj.dao.CommandRecordDao;
import com.yxkj.service.CommandRecordService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

import static com.yxkj.common.commonenum.CommonEnum.CmdType.SELL_OUT;
import static com.yxkj.common.commonenum.CommonEnum.CmdType.SELL_OUT_TEST;
import static com.yxkj.entity.commonenum.CommonEnum.ShipmentStatus.SHIPMENT_FAIL;
import static com.yxkj.entity.commonenum.CommonEnum.ShipmentStatus.SHIPMENT_SUCCESS;

@Service("commandRecordServiceImpl")
public class CommandRecordServiceImpl extends BaseServiceImpl<CommandRecord, Long>
    implements CommandRecordService {

  private CommandRecordDao commandRecordDao;
  @Resource(name = "orderItemServiceImpl")
  private OrderItemService orderItemService;

  @Resource(name = "shipmentExceptionServiceImpl")
  private ShipmentExceptionService shipmentExceptionService;

  @Resource(name = "commandRecordDaoImpl")
  public void setBaseDao(CommandRecordDao commandRecordDao) {
    super.setBaseDao(commandRecordDao);
    this.commandRecordDao = commandRecordDao;
  }

  @Override
  public CommandRecord updateCmdStatus(Long recordId, boolean isSuccess,
      String extMsg) {

    CommandRecord record = commandRecordDao.find(recordId);
    record.setCmdStatus(CommonEnum.CmdStatus.Finished);
    if (record.getCmdType().equals(SELL_OUT)) {
      orderItemService.updateOrderItemShipmentStatus(Long.valueOf(record.getCmdContent()),
          isSuccess ? SHIPMENT_SUCCESS : SHIPMENT_FAIL);
      if (!isSuccess)
        shipmentExceptionService.genShipmentException(record,extMsg);
    } else if (record.getCmdType().equals(SELL_OUT_TEST)) {
      if (!isSuccess)
        shipmentExceptionService.genShipmentException(record,extMsg);
    }
    return commandRecordDao.merge(record);
  }
}
