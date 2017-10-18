package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.service.OrderItemService;
import org.springframework.stereotype.Service;

import com.yxkj.entity.CommandRecord;
import com.yxkj.dao.CommandRecordDao;
import com.yxkj.service.CommandRecordService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

import static com.yxkj.entity.commonenum.CommonEnum.CmdType.SELL_OUT;
import static com.yxkj.entity.commonenum.CommonEnum.ShipmentStatus.SHIPMENT_FAIL;
import static com.yxkj.entity.commonenum.CommonEnum.ShipmentStatus.SHIPMENT_SUCCESS;

@Service("commandRecordServiceImpl")
public class CommandRecordServiceImpl extends BaseServiceImpl<CommandRecord, Long>
    implements CommandRecordService {

  private CommandRecordDao commandRecordDao;
  @Resource(name = "orderItemServiceImpl")
  private OrderItemService orderItemService;

  @Resource(name = "commandRecordDaoImpl")
  public void setBaseDao(CommandRecordDao commandRecordDao) {
    super.setBaseDao(commandRecordDao);
    this.commandRecordDao = commandRecordDao;
  }

  @Override
  public CommandRecord updateCmdStatus(Long recordId, boolean isSuccess) {

    CommandRecord record = commandRecordDao.find(recordId);
    record.setCmdStatus(CommonEnum.CmdStatus.Finished);
    if (record.getCmdType().equals(SELL_OUT)) {
      orderItemService.updateOrderItemShipmentStatus(Long.valueOf(record.getCmdContent()),
          isSuccess ? SHIPMENT_SUCCESS : SHIPMENT_FAIL);
    }
    return commandRecordDao.merge(record);
  }
}
