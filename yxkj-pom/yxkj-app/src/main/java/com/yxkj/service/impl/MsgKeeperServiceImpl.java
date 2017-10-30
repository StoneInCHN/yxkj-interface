package com.yxkj.service.impl; 

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yxkj.entity.MsgKeeper;
import com.yxkj.entity.commonenum.CommonEnum;
import com.ibm.icu.text.SimpleDateFormat;
import com.yxkj.dao.MsgKeeperDao;
import com.yxkj.service.MsgKeeperService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.bean.KeeperNotice;
import com.yxkj.json.bean.KeeperNoticeItem;

@Service("msgKeeperServiceImpl")
public class MsgKeeperServiceImpl extends BaseServiceImpl<MsgKeeper,Long> implements MsgKeeperService {
  
      @Resource(name="msgKeeperDaoImpl")
      private MsgKeeperDao msgKeeperDao;

      @Resource(name="msgKeeperDaoImpl")
      public void setBaseDao(MsgKeeperDao msgKeeperDao) {
         super.setBaseDao(msgKeeperDao);
      }

      @Override
      public List<KeeperNotice> getKeeperNotices(Long userId) {
        List<KeeperNotice> notices = new LinkedList<>();

        List<Object[]> typeCountlist = msgKeeperDao.countKeeperMsgType(userId);
        for (Object[] typeCount : typeCountlist) {
          KeeperNotice notice = new KeeperNotice();
          notice.setType(((CommonEnum.RemindType)typeCount[0]).toString());
          notice.setNoticeCount(((Long)typeCount[1]).intValue());
          List<Object[]> contents = msgKeeperDao.getKeeperMsgByType(userId, ((CommonEnum.RemindType)typeCount[0]).toString());
          Object[] firstMsg = contents.get(0);
          notice.setContent((String)firstMsg[0]+(String)firstMsg[1]);
          notice.setSceneSn((String)firstMsg[2]);
          notice.setNoticeTime(new SimpleDateFormat("yy/MM/dd hh:mm:ss").format((Date)firstMsg[3]));
          notices.add(notice);
        }
        return notices;
      }
      
      @Override
      public List<KeeperNoticeItem> getTypeNotices(Long userId, String type) {
        List<KeeperNoticeItem> items = new LinkedList<>();
        List<Object[]> contents = msgKeeperDao.getKeeperMsgByType(userId, type);
        for (Object[] content : contents) {
          KeeperNoticeItem noticeItem = new KeeperNoticeItem();
          noticeItem.setTitle((String)content[0]);
          noticeItem.setContent((String)content[1]);
          noticeItem.setSceneSn((String)content[2]);
          noticeItem.setSendDate(new SimpleDateFormat("yy/MM/dd hh:mm:ss").format((Date)content[3]));
          items.add(noticeItem);
        }
        return items;
      }
}