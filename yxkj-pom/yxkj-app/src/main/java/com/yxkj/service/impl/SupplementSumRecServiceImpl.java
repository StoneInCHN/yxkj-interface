package com.yxkj.service.impl; 

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.dao.SupplementSumRecDao;
import com.yxkj.service.SupplementSumRecService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.bean.DailySumSupplementRecord;
import com.yxkj.json.bean.DailySumSupplementRecord.SceneSumSupplementRecord;

@Service("supplementSumRecServiceImpl")
public class SupplementSumRecServiceImpl extends BaseServiceImpl<SupplementSumRec,Long> implements SupplementSumRecService {

      @Resource(name="supplementSumRecDaoImpl")
      private SupplementSumRecDao supplementSumRecDao;
      
      @Resource(name="supplementSumRecDaoImpl")
      public void setBaseDao(SupplementSumRecDao supplementSumRecDao) {
         super.setBaseDao(supplementSumRecDao);
      }

      @Override
      public List<DailySumSupplementRecord> findSupplySumRecord(Long suppId, String pageNo, int pageSize) {
        List<DailySumSupplementRecord> records = new LinkedList<>();
        
        List<Object> dateObjs = supplementSumRecDao.findSupplyDate(suppId, Integer.valueOf(pageNo).intValue(), pageSize);
        if (dateObjs == null)
          return records;
        for (Object date : dateObjs) {
          DailySumSupplementRecord record = new DailySumSupplementRecord();
          record.setDate((String)date);
          List<SceneSumSupplementRecord> sceneSumSupplementRecords = new LinkedList<>();
          
          List<Object[]> supplementSumRecords = supplementSumRecDao.findSupplementSumRecord(suppId, (String)date);
          for (Object[] supplyRecordObj : supplementSumRecords) {
            SceneSumSupplementRecord sceneSumSupplementRecord = record.new SceneSumSupplementRecord((String)supplyRecordObj[0],
                (String)supplyRecordObj[1], (Integer)supplyRecordObj[2], (Integer)supplyRecordObj[3], (String)supplyRecordObj[4]);
            sceneSumSupplementRecords.add(sceneSumSupplementRecord);
            record.setSumWaitSupplyCount(record.getSumWaitSupplyCount().intValue() + (Integer)supplyRecordObj[2]);
            record.setSumSupplyCount(record.getSumSupplyCount().intValue() + (Integer)supplyRecordObj[3]);
          }
          record.setSupplementList(sceneSumSupplementRecords);
          records.add(record);
        }
        return records;
      }
}