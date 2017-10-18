package com.yxkj.service.impl;

import com.yxkj.client.ReceiverClient;
import com.yxkj.common.log.LogUtil;
import com.yxkj.commonenum.CommonEnum;
import com.yxkj.dao.CmdDao;
import com.yxkj.entity.CmdMsg;
import com.yxkj.entity.CommandRecord;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.CmdService;
import com.yxkj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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

  private void saveCommandRecordList(List<CmdMsg> cmdMsgList) {
    cmdMsgList.forEach(cmdMsg -> {
      CommandRecord record = new CommandRecord();

      // 保存指令到数据库
      record.setCmdContent(cmdMsg.getContent().get("orderItemId"));
      record.setCmdType(cmdMsg.getType());
      record.setDeviceNo(cmdMsg.getDeviceNo());
      record.setCmdStatus(CommonEnum.CmdStatus.SendOut);
      cmdDao.persist(record);

      cmdMsg.setId(record.getId());

    });

  }

  private Long saveCommandRecord(String deviceNo, CommonEnum.CmdType cmdType, String content) {
    CommandRecord record = new CommandRecord();

    // 保存指令到数据库
    record.setCmdContent(content);
    record.setCmdType(cmdType);
    record.setDeviceNo(deviceNo);
    record.setCmdStatus(CommonEnum.CmdStatus.SendOut);
    cmdDao.persist(record);

    return record.getId();
  }

  @Override
  public void salesOut(Long orderId) {
    try {
      List<CmdMsg> cmdMsgList = orderService.salesOut(orderId);
      if (cmdMsgList != null && !cmdMsgList.isEmpty()) {
        saveCommandRecordList(cmdMsgList);
        receiverClient.sendCmdList(cmdMsgList);
        LogUtil.debug(this.getClass(), "updateAdv", "CmdMsg size %d,OrderId:%d", cmdMsgList.size(),
            orderId);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void updateAdv(String deviceNo, Map<String, String> map) {
    StringBuffer stringBuffer = new StringBuffer();
    map.forEach((k, v) -> {
      stringBuffer.append(k);
      stringBuffer.append("=");
      stringBuffer.append(v);
    });
    try {
      Long recordId =
          saveCommandRecord(deviceNo, CommonEnum.CmdType.AD_UPDATE, stringBuffer.toString());
      CmdMsg cmdMsg = receiverClient.updateAdv(deviceNo, map, recordId);
      LogUtil.debug(this.getClass(), "updateAdv", "CmdMsg = %s", cmdMsg.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateAudioVolume(String deviceNo, float volume) {
    try {
      Long recordId =
          saveCommandRecord(deviceNo, CommonEnum.CmdType.VOLUME, String.valueOf(volume));
      CmdMsg cmdMsg = receiverClient.updateAudioVolume(deviceNo, volume, recordId);
      LogUtil.debug(this.getClass(), "updateAudioVolume", "CmdMsg = %s", cmdMsg.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void notificationCmd(String deviceNo, CommonEnum.CmdType cmdType) {
    Long recordId = saveCommandRecord(deviceNo, cmdType, null);

    try {
      CmdMsg cmdMsg = receiverClient.notificationCmd(deviceNo, recordId, cmdType);

      LogUtil.debug(this.getClass(), "notificationCmd", "CmdMsg = %s", cmdMsg.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
