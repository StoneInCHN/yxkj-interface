package com.yxkj.service; 

import java.util.List;

import com.yxkj.entity.MsgKeeper;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.bean.KeeperNotice;
import com.yxkj.json.bean.KeeperNoticeItem;

public interface MsgKeeperService extends BaseService<MsgKeeper,Long>{
  
  List<KeeperNotice> getKeeperNotices(Long userId);
  
  List<KeeperNoticeItem> getTypeNotices(Long userId, String type);
  
}