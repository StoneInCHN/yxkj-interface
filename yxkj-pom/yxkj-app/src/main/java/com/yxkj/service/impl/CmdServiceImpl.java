package com.yxkj.service.impl;

import java.io.IOException;

import javax.annotation.Resource;

import com.yxkj.service.VendingContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxkj.common.client.ReceiverClient;
import com.yxkj.common.commonenum.CommonEnum;
import com.yxkj.common.entity.CmdMsg;
import com.yxkj.common.log.LogUtil;
import com.yxkj.dao.CommandRecordDao;
import com.yxkj.entity.CommandRecord;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.CmdService;
import com.yxkj.service.ContainerChannelService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huyong
 * @since 2017/9/26
 */
@Service("cmdServiceImpl")
public class CmdServiceImpl extends BaseServiceImpl<CommandRecord, Long> implements CmdService {

  private CommandRecordDao commandRecordDao;

  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;

  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;
  private ReceiverClient receiverClient;

  @Autowired
  public CmdServiceImpl(ReceiverClient receiverClient, CommandRecordDao commandRecordDao) {
    this.commandRecordDao = commandRecordDao;
    this.receiverClient = receiverClient;
  }

  private Long saveCommandRecord(String deviceNo, CommonEnum.CmdType cmdType, String content) {
    CommandRecord record = new CommandRecord();

    // 保存指令到数据库
    record.setCmdContent(content);
    record.setCmdType(cmdType);
    record.setDeviceNo(deviceNo);
    record.setCmdStatus(CommonEnum.CmdStatus.SendOut);
    commandRecordDao.persist(record);

    return record.getId();
  }

  @Override
  public void salesOutTest(Long channelId) {

    ContainerChannel containerChannel = containerChannelService.find(channelId);
    VendingContainer vendingContainer = containerChannel.getCntr().getParent();
    String deviceNo = vendingContainer.getSn();
    String address = containerChannel.getCntr().getSn();
    int addressType = containerChannel.getCntr().getCategory().getCntrType().ordinal();
    StringBuilder content = new StringBuilder();
    content.append("channelId:");
    content.append(channelId);
    Long recordId = saveCommandRecord(deviceNo, CommonEnum.CmdType.SELL_OUT_TEST, content.toString());
    try {
      receiverClient.sellOutTest(deviceNo, recordId, address, addressType,
          containerChannel.getSn());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void updateAudioVolume(String deviceNo, String volume) {
    try {
      Long recordId =
          saveCommandRecord(deviceNo, CommonEnum.CmdType.VOLUME, String.valueOf(volume));
      CmdMsg cmdMsg =
          receiverClient.updateAudioVolume(deviceNo, Float.parseFloat(volume), recordId);
      VendingContainer vendingContainer = vendingContainerService.getByImei(deviceNo);
      vendingContainer.setVolume(volume);
      vendingContainerService.update(vendingContainer);
      LogUtil.debug(this.getClass(), "updateAudioVolume", "CmdMsg = %s", cmdMsg.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void appReboot(String deviceNo) {
    try {
      Long recordId = saveCommandRecord(deviceNo, CommonEnum.CmdType.VOLUME, null);
      CmdMsg cmdMsg = receiverClient.reboot(deviceNo, recordId);
      LogUtil.debug(this.getClass(), "updateAudioVolume", "CmdMsg = %s", cmdMsg.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
