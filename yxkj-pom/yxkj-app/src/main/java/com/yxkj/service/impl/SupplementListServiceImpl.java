package com.yxkj.service.impl; 

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.entity.SupplementList;
import com.yxkj.entity.SupplementPic;
import com.yxkj.entity.SupplementRecord;
import com.yxkj.entity.SupplementSumRec;
import com.yxkj.dao.ContainerKeeperDao;
import com.yxkj.dao.GoodsPicDao;
import com.yxkj.dao.SceneDao;
import com.yxkj.dao.SupplementListDao;
import com.yxkj.dao.SupplementPicDao;
import com.yxkj.dao.SupplementRecordDao;
import com.yxkj.dao.SupplementSumRecDao;
import com.yxkj.dao.VendingContainerDao;
import com.yxkj.service.SupplementListService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.bean.SupplementSumRecord;
import com.yxkj.json.bean.SupplyRecord;
import com.yxkj.json.bean.WaitSupplyContainerGoods;
import com.yxkj.json.bean.WaitSupplyGoods;
import com.yxkj.json.bean.WaitSupplyGoodsDetails;
import com.yxkj.json.bean.WaitSupplyList;

@Service("supplementListServiceImpl")
public class SupplementListServiceImpl extends BaseServiceImpl<SupplementList,Long> implements SupplementListService {

      @Resource(name="sceneDaoImpl")
      private SceneDao sceneDao;
      
      @Resource(name="goodsPicDaoImpl")
      private GoodsPicDao goodsPicDao;
      
      @Resource(name="supplementListDaoImpl")
      private SupplementListDao supplementListDao;
      
      @Resource(name="vendingContainerDaoImpl")
      private VendingContainerDao vendingContainerDao;
      
      @Resource(name="supplementRecordDaoImpl")
      private SupplementRecordDao supplementRecordDao;
      
      @Resource(name="supplementSumRecDaoImpl")
      private SupplementSumRecDao supplementSumRecDao;
      
      @Resource(name="supplementPicDaoImpl")
      private SupplementPicDao supplementPicDao;
      
      @Resource(name="containerKeeperDaoImpl")
      private ContainerKeeperDao containerKeeperDao;
      
      @Resource(name="supplementListDaoImpl")
      public void setBaseDao(SupplementListDao supplementListDao) {
         super.setBaseDao(supplementListDao);
      }
      
      //获取待补货清单
      public WaitSupplyList getWaitSupplyListBySuppId(Long suppId, String pageNo, int pageSize) {
        
        WaitSupplyList waitSupplyList = new WaitSupplyList();
        
        int waitSupplySumCount = 0;
        List<Object> countList = supplementListDao.findWaitSupplyCountBySuppId(suppId);
        for (Object count : countList) {
          waitSupplySumCount += (Integer)count;
        }
        List<WaitSupplyList.WaitSupplyScene> scenesList = new LinkedList<>();
        
        //获取优享空间列表
        List<Object[]> sceneList = sceneDao.findWaitSupplyScene(suppId, Integer.valueOf(pageNo).intValue(), pageSize);
        for(Object[] sceneObjs : sceneList) {
          String sceneSn = (String)sceneObjs[0];
          String sceneName = (String)sceneObjs[1];
          WaitSupplyList.WaitSupplyScene scene = waitSupplyList.new WaitSupplyScene(sceneSn, sceneName);
          List<WaitSupplyList.WaitSupplyScene.VendingContainerGroup> groupList = new LinkedList<>();
          
          //获取所有所有中控柜
          List<Object[]> centralContainerList = vendingContainerDao.findCentralVendingContainer(sceneSn);
          for(Object[] centralContainer : centralContainerList){
            WaitSupplyList.WaitSupplyScene.VendingContainerGroup group = scene.new VendingContainerGroup();
            List<WaitSupplyList.WaitSupplyScene.VendingContainerGroup.WaitSupplyVendingContainer> containerList = new LinkedList<>();
            
            //获取中控柜
            WaitSupplyList.WaitSupplyScene.VendingContainerGroup.WaitSupplyVendingContainer central = group
                .new WaitSupplyVendingContainer(((BigInteger)centralContainer[0]).longValue(), (String)centralContainer[1]);
            central.setCentral(true);
            
            //获取子货柜
            List<Object[]> childContainerList = vendingContainerDao.findChildrenVendingContainer(((BigInteger)centralContainer[0]).longValue());
            for(Object[] childContainer : childContainerList) {
              WaitSupplyList.WaitSupplyScene.VendingContainerGroup.WaitSupplyVendingContainer child = group
                  .new WaitSupplyVendingContainer(((BigInteger)childContainer[0]).longValue(), (String)childContainer[1]);
              int count = 0;
              try{
                  count = supplementListDao.findWaitSupplyCountByCntrId(((BigInteger)childContainer[0]).longValue());
              }catch (Exception e) {}
              child.setWaitSupplyCount(count);
              containerList.add(child);
            }
            containerList.add(central);
            //添加货柜组
            group.setVendingContainers(containerList);
            groupList.add(group);
          }
          //添加优享空间
          scene.setVendingContainerGroups(groupList);
          scenesList.add(scene);
        }
        waitSupplyList.setScenes(scenesList);
        waitSupplyList.setWaitSupplySumCount(waitSupplySumCount);
        return waitSupplyList;
      }

      @Override
      public List<Map<String, String>> getWaitSupplySceneList(Long suppId) {
        List<Map<String, String>> sceneList = new LinkedList<>();
        
        List<Object[]> waitSupplySceneList = supplementListDao.findWaitSupplySceneList(suppId);
        for(Object[] sceneObjs : waitSupplySceneList) {
          Map<String, String> sceneMap = new HashMap<>();
          sceneMap.put("sceneSn", (String)sceneObjs[0]);
          sceneMap.put("sceneName", (String)sceneObjs[1]);
          sceneList.add(sceneMap);
        }
        return sceneList;
      }
      
      @Override
      public List<WaitSupplyGoods> getWaitSupplyGoodList(Long suppId, String sceneSn, Long cateId,
          String pageNo, int pageSize) {
        List<WaitSupplyGoods> waitSupplyGoodList = new LinkedList<>();
        
        List<Object[]> goodsList = supplementListDao.findWaitSupplyGoodsList(suppId, sceneSn, cateId,
            Integer.valueOf(pageNo), pageSize);
        for(Object[] goodsObj : goodsList) {
          WaitSupplyGoods waitSupplyGoods = new WaitSupplyGoods((String)goodsObj[0],
              (String)goodsObj[1], ((Long)goodsObj[2]).intValue());
          waitSupplyGoods.setGoodsPic((String)goodsPicDao.findGoodsPicByGoodsSn((String)goodsObj[0]));
          waitSupplyGoodList.add(waitSupplyGoods);
        }
        return waitSupplyGoodList;
      }
      
      @Override
      public List<Map<String,Object>> getWaitSupplyGoodsCategoryList(Long  suppId) {
        List<Map<String,Object>> waitSupplyGoodsCategoryList = new LinkedList<>();
        
        List<Object[]> categoryList = supplementListDao.findWaitSupplyGoodsCategoryList(suppId);
        for (Object[] category : categoryList) {
          Map<String, Object> cateMap = new HashMap<>();
          cateMap.put("cateId", category[0]);
          cateMap.put("cateName", category[1]);
          waitSupplyGoodsCategoryList.add(cateMap);
        }
        return waitSupplyGoodsCategoryList;
      }

      @Override
      public WaitSupplyGoodsDetails getWaitSupplyGoodsDetails(Long suppId, String goodsSn) {
        Integer sumCount = 0;
        List<Map<String, Object>> sceneCountList = new LinkedList<>();
        List<Object[]> goodsDetailsList = supplementListDao.findWaitSupplyGoodsDetails(suppId, goodsSn);
        String goodsName = (String)goodsDetailsList.get(0)[0];
        for (Object[] goodsDetails : goodsDetailsList) {
          Map<String, Object> sceneCountMap = new HashMap<>();
          Integer waitSupplyCount = (Integer)goodsDetails[2];
          sceneCountMap.put("sceneName", goodsDetails[1]);
          sceneCountMap.put("waitSupplyCount", waitSupplyCount);
          sceneCountList.add(sceneCountMap);
          sumCount += waitSupplyCount;
        }
        return new WaitSupplyGoodsDetails(goodsSn, goodsName, sceneCountList, sumCount);
      }

      @Override
      public List<WaitSupplyContainerGoods> getWaitSupplyContainerGoods(Long suppId, Long cntrId,
          String pageNo, int pageSize) {
        List<WaitSupplyContainerGoods> waitSupplyContainerGoodsList = new LinkedList<>();
        List<Object[]> containerGoodsList = supplementListDao.getWaitSupplyContainerGoods(suppId, cntrId, 
            Integer.valueOf(pageNo), pageSize);
        for(Object[] goodsObj : containerGoodsList) {
          WaitSupplyContainerGoods waitSupplyContainerGoods = new WaitSupplyContainerGoods(((BigInteger)goodsObj[0]).longValue(),
              (String)goodsObj[1], (String)goodsObj[2],(String)goodsObj[3], (Integer)goodsObj[4], (Integer)goodsObj[5]);
          waitSupplyContainerGoods.setGoodsPic((String)goodsPicDao.findGoodsPicByGoodsSn((String)goodsObj[1]));
          waitSupplyContainerGoodsList.add(waitSupplyContainerGoods);
        }
        return waitSupplyContainerGoodsList;
      }

      @Override
      @Transactional
      public void commitSupplyRecords(Long suppId, String sceneSn, List<SupplyRecord> records) throws Exception {
        Integer suppCount = 0;
        //查询优享空间的汇总记录，若没有，则新建
        SupplementSumRec supplementSumRec = null;
        try {
          supplementSumRec = supplementSumRecDao.findSupplementSumRecordBySceneSn(suppId, sceneSn);
        } catch (Exception e) {
          supplementSumRec = new SupplementSumRec();
          supplementSumRec.setSceneSn(sceneSn);
          supplementSumRec.setSceneName((String)sceneDao.findSceneNameBySn(sceneSn));
          ContainerKeeper keeper = containerKeeperDao.find(suppId);
          List<Object> countList = supplementListDao.findWaitSupplyCountSceneSn(sceneSn);
          supplementSumRec.setSuppId(suppId);
          supplementSumRec.setSuppMobile(keeper.getCellPhoneNum());
          supplementSumRec.setSuppName(keeper.getUserName());
          //设置开始时间
          supplementSumRec.setSuppStartTime(new Date());
          Integer waitSuppTotalCount = 0;
          for (Object count : countList) {
            waitSuppTotalCount += (Integer)count;
          }
          supplementSumRec.setWaitSuppTotalCount(waitSuppTotalCount);
          supplementSumRec.setLackCount(waitSuppTotalCount);
          supplementSumRec.setSuppTotalCount(0);
          supplementSumRecDao.persist(supplementSumRec);
        }
        for (SupplyRecord record : records) {
          SupplementList supplementList = supplementListDao.find(record.getSupplementId());
          SupplementRecord supplementRecord = new SupplementRecord();
//          supplementRecord.setChannel(supplementList.getChannel());
          supplementRecord.setCntrId(supplementList.getCntrId());
          supplementRecord.setCntrSn(supplementList.getCntrSn());
          supplementRecord.setSceneId(supplementList.getSceneId());
          supplementRecord.setSceneName(supplementList.getSceneName());
          supplementRecord.setSceneSn(supplementList.getSceneSn());
          supplementRecord.setSuppId(supplementList.getSuppId());
          supplementRecord.setGoodsSn(supplementList.getGoodsSn());
          supplementRecord.setGoodsName(supplementList.getGoodsName());
          supplementRecord.setWaitSupplyCount(supplementList.getWaitSupplyCount());
          supplementRecord.setSupplyCount(record.getSupplyCount());
          if(record.getSupplyCount() < supplementList.getWaitSupplyCount()) {
            //更新清单待补数量
            supplementList.setWaitSupplyCount(supplementList.getWaitSupplyCount() - record.getSupplyCount());
            supplementListDao.merge(supplementList);
          } else if (record.getSupplyCount() == supplementList.getWaitSupplyCount()) {
            //删除该待补清单
            supplementListDao.remove(supplementList);
          } else {
            throw new Exception();
          }
          //添加补货记录
          supplementRecordDao.persist(supplementRecord);
          suppCount += record.getSupplyCount();
        }
        //更新汇总记录
        supplementSumRec.setSuppTotalCount(supplementSumRec.getSuppTotalCount() + suppCount);
        supplementSumRec.setLackCount(supplementSumRec.getLackCount() - suppCount);
        supplementSumRecDao.merge(supplementSumRec);
      }

      @Override
      @Transactional
      public void uploadSupplementPic(Long suppId, Long cntrId, String picFileName) {
        SupplementPic supplementPic = new SupplementPic();
        supplementPic.setSource(picFileName);
        supplementPicDao.persist(supplementPic);
        List<SupplementRecord> records = supplementRecordDao.findRecordByCntrId(suppId, cntrId);
        System.out.println(records.size());
        for (SupplementRecord supplementRecord : records) {
          supplementRecord.setSuppPic(supplementPic);
          supplementRecordDao.merge(supplementRecord);
        }
      }

      @Override
      public List<SupplementSumRecord> getSupplementSumRecord(String pageNo, String pageSize, Long suppId) {
        List<SupplementSumRec> sumRec = supplementSumRecDao.findSupplementSumRecord(
            Integer.valueOf(pageNo).intValue(), Integer.valueOf(pageSize).intValue(), suppId);
        for (SupplementSumRec supplementSumRec : sumRec) {
          System.out.println(supplementSumRec.getSuppEndTime());
        }
        return null;
      }
      
}