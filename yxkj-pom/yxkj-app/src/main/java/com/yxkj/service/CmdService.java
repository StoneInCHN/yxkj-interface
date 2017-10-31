package com.yxkj.service;

import java.util.Map;

import com.yxkj.common.commonenum.CommonEnum;
import com.yxkj.entity.CommandRecord;
import com.yxkj.framework.service.BaseService;

/**
 * @author huyong
 * @since 2017/9/26
 */
public interface CmdService extends BaseService<CommandRecord, Long> {

  void salesOutTest(Long cntrId);


  void updateAudioVolume(String deviceNo, String volume);


  void appReboot(String deviceNo);
}
