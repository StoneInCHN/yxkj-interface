package com.yxkj.service.impl;

import com.yxkj.client.ReceiverClient;
import com.yxkj.dao.CmdDao;
import com.yxkj.entity.CmdMsg;
import com.yxkj.entity.CommandRecord;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.CmdService;
import com.yxkj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huyong
 * @since 2017/9/26
 */
@Service("cmdServiceImpl")
public class CmdServiceImpl extends BaseServiceImpl<CommandRecord, Long> implements CmdService {

  @Resource(name = "orderServiceImpl")
  private OrderService orderService;
  private CmdDao cmdDao;

  private ReceiverClient receiverClient;

  @Autowired
  public CmdServiceImpl(ReceiverClient receiverClient, CmdDao cmdDao) {
    this.cmdDao = cmdDao;
    this.receiverClient = receiverClient;
  }

  private void saveCommandRecord(CmdMsg cmdMsg) {
    CommandRecord record = new CommandRecord();

    // 保存指令到数据库
    record.setCmdContent(cmdMsg.getContentString());
    record.setCmdType(CommonEnum.CmdType.values()[cmdMsg.getType()]);
    record.setDeviceNo(cmdMsg.getDeviceNo());
    record.setCmdStatus(CommonEnum.CmdStatus.SendOut);
    cmdDao.persist(record);

    cmdMsg.setId(record.getId());
  }

  @Override
  public void salesOut(Long orderId) {
    try {
      List<CmdMsg> cmdMsgList = orderService.salesOut(orderId);
      if (cmdMsgList != null && !cmdMsgList.isEmpty()) {
        List<CmdMsg> cmdMsgResults = receiverClient.salesOut(cmdMsgList);
        cmdMsgResults.forEach(this::saveCommandRecord);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void updateAdv(String deviceNo, Map<String, String> map) {

    try {
      CmdMsg cmdMsg = receiverClient.updateAdv(deviceNo, map);
      saveCommandRecord(cmdMsg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateAudioVolume(String deviceNo, float volume) {
    try {
      CmdMsg cmdMsg = receiverClient.updateAudioVolume(deviceNo, volume);
      saveCommandRecord(cmdMsg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
