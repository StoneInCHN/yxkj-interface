package com.yxkj.service.impl; 

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementRecord;
import com.yxkj.dao.GoodsPicDao;
import com.yxkj.dao.SupplementRecordDao;
import com.yxkj.service.SupplementRecordService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.bean.SceneSupplementRecord;
import com.yxkj.json.bean.SceneSupplementRecord.CntrSupplementRecord;

@Service("supplementRecordServiceImpl")
public class SupplementRecordServiceImpl extends BaseServiceImpl<SupplementRecord,Long> implements SupplementRecordService {

      @Resource(name="supplementRecordDaoImpl")
      private SupplementRecordDao supplementRecordDao;
      
      @Resource(name="goodsPicDaoImpl")
      private GoodsPicDao goodsPicDao;
  
      @Resource(name="supplementRecordDaoImpl")
      public void setBaseDao(SupplementRecordDao supplementRecordDao) {
         super.setBaseDao(supplementRecordDao);
      }

      @Override
      public List<SceneSupplementRecord> getSupplementRecordDetails(Long userId, String sceneSn) {
        List<SceneSupplementRecord> records = new LinkedList<>();
        List<Object[]> cntrObjs = supplementRecordDao.findSupplementVendingContainerBySceneSn(userId, sceneSn);
        if (cntrObjs == null)
          return records;
        for (Object[] objs : cntrObjs) {
          SceneSupplementRecord record = new SceneSupplementRecord();
          record.setCntrSn((String)objs[1]);
          List<CntrSupplementRecord> list = new LinkedList<>(); 
          List<Object[]> cntrRecords = supplementRecordDao.findSupplementGoodsRecordByCntrId(userId, (Long)objs[0]);
          for (Object[] objects : cntrRecords) {
            CntrSupplementRecord rec = record.new CntrSupplementRecord();
            rec.setChannelSn((String)objects[0]);
            rec.setGoodsPic((String)goodsPicDao.findGoodsPicByGoodsSn((String)objects[1]));
            rec.setGoodsName((String)objects[2]);
            rec.setWaitSupplyCount((Integer)objects[3]);
            rec.setSupplyCount((Integer)objects[4]);
            list.add(rec);
          }
          record.setCntrSupplementRecords(list);
          records.add(record);
        }
        return records;
      }
}