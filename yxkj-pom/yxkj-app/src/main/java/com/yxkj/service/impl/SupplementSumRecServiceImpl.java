package com.yxkj.service.impl; 

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementSumRec;
import com.ibm.icu.text.SimpleDateFormat;
import com.yxkj.dao.SupplementSumRecDao;
import com.yxkj.service.SupplementSumRecService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.bean.SumSupplementRecord;

@Service("supplementSumRecServiceImpl")
public class SupplementSumRecServiceImpl extends BaseServiceImpl<SupplementSumRec,Long> implements SupplementSumRecService {

      @Resource(name="supplementSumRecDaoImpl")
      private SupplementSumRecDao supplementSumRecDao;
      
      @Resource(name="supplementSumRecDaoImpl")
      public void setBaseDao(SupplementSumRecDao supplementSumRecDao) {
         super.setBaseDao(supplementSumRecDao);
      }

      @Override
      public List<SumSupplementRecord> findSupplySumRecord(Long suppId, String pageNo, int pageSize) {
        List<SumSupplementRecord> records = new LinkedList<>();
        List<Object[]> sumRecords = supplementSumRecDao.findSupplementSumRecord(suppId, Integer.valueOf(pageNo), pageSize);
        for (Object[] sumRecord : sumRecords) {
          SumSupplementRecord record = new SumSupplementRecord((String)sumRecord[0], (String)sumRecord[1], (Integer)sumRecord[2], 
              (Integer)sumRecord[3], (Integer)sumRecord[4], new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format((Date)sumRecord[5]));
          records.add(record);
        }
        return records;
      }
}