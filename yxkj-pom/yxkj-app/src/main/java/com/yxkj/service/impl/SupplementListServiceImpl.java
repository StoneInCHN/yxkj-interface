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
import com.yxkj.dao.GoodsDao;
import com.yxkj.dao.GoodsPicDao;
import com.yxkj.dao.SceneDao;
import com.yxkj.dao.SupplementListDao;
import com.yxkj.dao.SupplementPicDao;
import com.yxkj.dao.SupplementRecordDao;
import com.yxkj.dao.SupplementSumRecDao;
import com.yxkj.dao.VendingContainerDao;
import com.yxkj.service.SupplementListService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.bean.SupplyRecord;
import com.yxkj.json.bean.WaitSupplyContainerGoods;
import com.yxkj.json.bean.WaitSupplyGoods;
import com.yxkj.json.bean.WaitSupplyGoodsDetails;
import com.yxkj.json.bean.WaitSupplyList;

@Service("supplementListServiceImpl")
public class SupplementListServiceImpl extends BaseServiceImpl<SupplementList,Long> implements SupplementListService {

      @Resource(name="sceneDaoImpl")
      private SceneDao sceneDao;
      
      @Resource(name="goodsDaoImpl")
      private GoodsDao goodsDao;
      
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
      
      //获取待补优享空间列表
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
      
      //获取待补商品列表
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
      
      //获取待补商品类别列表
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

      //获取待补商品详情
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
      
      //开始补货
      @Override
      public Object[] startSupplyGoods(Long suppId, String sceneSn) {
        Object[] objs = null;
        SupplementSumRec supplementSumRec = null;
        try {
          objs = supplementSumRecDao.findUnfinishedSupplyRecord(suppId, sceneSn);
          return objs;
        } catch (Exception e) {}
        try {
          supplementSumRec = supplementSumRecDao.findSupplementSumRecordBySceneSn(suppId, sceneSn);
        } catch (Exception ex) {
          supplementSumRec = new SupplementSumRec();
        }
        supplementSumRec.setSceneSn(sceneSn);
        supplementSumRec.setSceneName((String)sceneDao.findSceneNameBySn(sceneSn));
        ContainerKeeper keeper = containerKeeperDao.find(suppId);
        supplementSumRec.setSuppId(suppId);
        supplementSumRec.setSuppMobile(keeper.getCellPhoneNum());
        supplementSumRec.setSuppName(keeper.getUserName());
        //设置开始时间
        supplementSumRec.setSuppStartTime(new Date());
        List<Object> countList = supplementListDao.findWaitSupplyCountSceneSn(sceneSn);
        Integer waitSuppTotalCount = 0;
        for (Object count : countList) {
          waitSuppTotalCount += (Integer)count;
        }
        supplementSumRec.setWaitSuppTotalCount(waitSuppTotalCount);
        supplementSumRec.setLackCount(waitSuppTotalCount);
        supplementSumRec.setSuppTotalCount(0);
        supplementSumRecDao.persist(supplementSumRec);
        return null;
      }

      //获取货柜待补商品
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
      
      //获取全部商品
      @Override
      public List<WaitSupplyContainerGoods> getContainerGoodsList(Long cntrId, String pageNo, int pageSize) {
        List<WaitSupplyContainerGoods> goodsList = new LinkedList<>();
        List<Object[]> objects = goodsDao.getContainerGoodsList(cntrId, Integer.valueOf(pageNo), pageSize);
        for (Object[] goodsObj : objects) {
          String goodsSn = (String)goodsObj[2];
          WaitSupplyContainerGoods goods = new WaitSupplyContainerGoods((Long)goodsObj[0], (String)goodsObj[1],
              (String)goodsObj[2], (String)goodsObj[3], (Integer)goodsObj[5], (Integer)goodsObj[6]);
          goods.setGoodsPic((String)goodsPicDao.findGoodsPicByGoodsSn(goodsSn));
        }
        return goodsList;
      }

      //提交补货记录
      @Override
      @Transactional
      public void commitSupplyRecords(Long suppId, String sceneSn, List<SupplyRecord> records) throws Exception {
        Integer suppCount = 0;
        SupplementSumRec supplementSumRec = supplementSumRecDao.findSupplementSumRecordBySceneSn(suppId, sceneSn);
        
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
          supplementRecord.setSuppSum(supplementSumRec);
          //添加补货记录
          supplementRecordDao.persist(supplementRecord);
          suppCount += record.getSupplyCount();
        }
        //更新汇总记录
        supplementSumRec.setSuppTotalCount(supplementSumRec.getSuppTotalCount() + suppCount);
        supplementSumRec.setLackCount(supplementSumRec.getLackCount() - suppCount);
        supplementSumRecDao.merge(supplementSumRec);
      }
      
      //上传补货图片
      @Override
      @Transactional
      public void uploadSupplementPic(Long suppId, Long cntrId, String picPath) {
        SupplementPic supplementPic = new SupplementPic();
        supplementPic.setSource(picPath);
        supplementPicDao.persist(supplementPic);
        List<SupplementRecord> records = supplementRecordDao.findRecordByCntrId(suppId, cntrId);
        System.out.println(records.size());
        for (SupplementRecord supplementRecord : records) {
          supplementRecord.setSuppPic(supplementPic);
          supplementRecordDao.merge(supplementRecord);
        }
      }

      //完成补货
      @Override
      public Object[] finishSupplyGoods(Long suppId, String sceneSn) {
        try {
          Object[] objs = supplementSumRecDao.findUnfinishedSupplyRecord(suppId, sceneSn);
          if(objs != null) return objs;
        } catch (Exception e) {}
        SupplementSumRec supplementSumRec = supplementSumRecDao.findSupplementSumRecordBySceneSn(suppId, sceneSn);
        supplementSumRec.setSuppEndTime(new Date());
        supplementSumRecDao.persist(supplementSumRec);
        return null;
      }

}