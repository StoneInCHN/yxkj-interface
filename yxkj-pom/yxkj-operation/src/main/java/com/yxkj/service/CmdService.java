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


  void updateAdv(String deviceNo, Map<String, String> map);

  void updateAudioVolume(String deviceNo, float volume);

  void notificationCmd(String deviceNo, CommonEnum.CmdType cmdType);

  void appUpdate(String deviceNo, String url);
}
