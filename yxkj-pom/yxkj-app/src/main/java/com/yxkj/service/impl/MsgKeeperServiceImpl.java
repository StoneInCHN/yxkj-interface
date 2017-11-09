package com.yxkj.service.impl; 

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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
        List<Object> typelist = msgKeeperDao.findKeeperMsgType(userId);
        if (typelist == null) return notices;
        for (Object type : typelist) {
          KeeperNotice notice = new KeeperNotice();
          String msgType = ((CommonEnum.RemindType)type).toString();
          notice.setType(msgType);
          notice.setNoticeCount(msgKeeperDao.countUnReadKeeperMsg(userId, msgType));
          List<Object[]> msgs = msgKeeperDao.getKeeperMsgByType(userId, msgType);
          Object[] firstMsg = msgs.get(0);
          notice.setContent((String)firstMsg[0]+(String)firstMsg[1]);
          notice.setNoticeTime(new SimpleDateFormat("yy/MM/dd hh:mm:ss").format((Date)firstMsg[3]));
          notices.add(notice);
        }
        return notices;
      }
      
      @Override
      @Transactional
      public List<KeeperNoticeItem> getTypeNotices(Long userId, String msgType) {
        List<KeeperNoticeItem> items = new LinkedList<>();
        List<MsgKeeper> msgKeepers = msgKeeperDao.getMsgKeeperByType(userId, msgType);
        if (msgKeepers != null) {
          for (MsgKeeper msgKeeper : msgKeepers) {
            msgKeeper.setIsRead(true);
          }
          msgKeeperDao.persist(msgKeepers);
        }
        List<Object[]> msgs = msgKeeperDao.getKeeperMsgByType(userId, msgType);
        if (msgs == null) return items;
        for (Object[] msg : msgs) {
          KeeperNoticeItem noticeItem = new KeeperNoticeItem();
          noticeItem.setTitle((String)msg[0]);
          noticeItem.setContent((String)msg[1]);
          noticeItem.setSceneSn((String)msg[2]);
          noticeItem.setSendDate(new SimpleDateFormat("yy/MM/dd hh:mm:ss").format((Date)msg[3]));
          items.add(noticeItem);
        }
        return items;
      }
}