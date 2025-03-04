package com.yxkj.service.impl; 

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.entity.KeeperRemindMsg;
import com.yxkj.entity.SupplementList;
import com.yxkj.entity.SupplementPic;
import com.yxkj.entity.SupplementRecord;
import com.yxkj.entity.SupplementSumRec;
import com.yxkj.entity.commonenum.CommonEnum.RemindType;
import com.yxkj.dao.ContainerChannelDao;
import com.yxkj.dao.ContainerKeeperDao;
import com.yxkj.dao.GoodsPicDao;
import com.yxkj.dao.KeeperRemindMsgDao;
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

      @Resource(name="keeperRemindMsgDaoImpl")
      private KeeperRemindMsgDao keeperRemindMsgDao;
      
      @Resource(name="containerChannelDaoImpl")
      private ContainerChannelDao containerChannelDao;
      
      @Resource(name="supplementListDaoImpl")
      public void setBaseDao(SupplementListDao supplementListDao) {
         super.setBaseDao(supplementListDao);
      }
      
      //获取待补货清单
      public WaitSupplyList getWaitSupplyListBySuppId(Long suppId, String pageNo, int pageSize) {
        WaitSupplyList waitSupplyList = new WaitSupplyList();
        
        int waitSupplySumCount = 0;
        List<Object> countList = supplementListDao.findWaitSupplyCountBySuppId(suppId);
        if (countList != null)
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
              List<Object> list = supplementListDao.findWaitSupplyCountByCntrId(((BigInteger)childContainer[0]).longValue());
              if (list != null) 
                for (Object object : list) {
                  count += (Integer)object;
                }
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
        if (waitSupplySceneList ==null)
          return sceneList;
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
        if (goodsList == null)
          return waitSupplyGoodList;
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
        if (categoryList == null)
          return waitSupplyGoodsCategoryList;
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
        Object[] objs = supplementSumRecDao.findUnfinishedSupplyRecord(suppId, sceneSn);;
        if (objs != null) return objs; 
        
        SupplementSumRec supplementSumRec = supplementSumRecDao.findSupplementSumRecordBySceneSn(suppId, sceneSn);
        if (supplementSumRec == null) {
          supplementSumRec = new SupplementSumRec();
          supplementSumRec.setSceneSn(sceneSn);
          supplementSumRec.setSceneName((String)sceneDao.findSceneNameBySn(sceneSn));
          ContainerKeeper keeper = containerKeeperDao.find(suppId);
          supplementSumRec.setSuppId(suppId);
          supplementSumRec.setSuppMobile(keeper.getCellPhoneNum());
          supplementSumRec.setSuppName(keeper.getUserName());
          supplementSumRec.setSuppStartTime(new Date());
          supplementSumRec.setSuppTotalCount(0);
          Integer waitSuppTotalCount = 0;
          List<Object> countList = supplementListDao.findWaitSupplyCountSceneSn(sceneSn);
          for (Object count : countList) {
            waitSuppTotalCount += (Integer)count;
          }
          supplementSumRec.setWaitSuppTotalCount(waitSuppTotalCount);
          supplementSumRec.setLackCount(waitSuppTotalCount);
          supplementSumRecDao.persist(supplementSumRec);
        }
        return null;
      }

      //获取货柜待补商品
      @Override
      public List<WaitSupplyContainerGoods> getWaitSupplyContainerGoods(Long suppId, Long cntrId,
          String pageNo, int pageSize) {
        List<WaitSupplyContainerGoods> waitSupplyContainerGoodsList = new LinkedList<>();
        List<Object[]> containerGoodsList = supplementListDao.getWaitSupplyContainerGoods(suppId, cntrId, 
            Integer.valueOf(pageNo), pageSize);
        if (containerGoodsList == null)
          return waitSupplyContainerGoodsList;
        for(Object[] goodsObj : containerGoodsList) {
          WaitSupplyContainerGoods waitSupplyContainerGoods = new WaitSupplyContainerGoods(((BigInteger)goodsObj[0]).longValue(),
              (String)goodsObj[1], (String)goodsObj[2],(String)goodsObj[3], (Integer)goodsObj[4], (Integer)goodsObj[5]);
          waitSupplyContainerGoods.setGoodsPic((String)goodsPicDao.findGoodsPicByGoodsSn((String)goodsObj[2]));
          waitSupplyContainerGoodsList.add(waitSupplyContainerGoods);
        }
        return waitSupplyContainerGoodsList;
      }
      
      //获取全部商品
      @Override
      public List<WaitSupplyContainerGoods> getContainerGoodsList(Long cntrId, String pageNo, int pageSize) {
        List<WaitSupplyContainerGoods> goodsList = new LinkedList<>();
        List<Object[]> objects = containerChannelDao.getContainerGoodsList(cntrId, Integer.valueOf(pageNo), pageSize);
        if (objects == null)
          return goodsList;
        for (Object[] goodsObj : objects) {
          String goodsSn = (String)goodsObj[2];
          WaitSupplyContainerGoods goods = new WaitSupplyContainerGoods((Long)goodsObj[0], (String)goodsObj[1],
              (String)goodsObj[2], (String)goodsObj[3], (Integer)goodsObj[4], (Integer)goodsObj[5]);
          goods.setGoodsPic((String)goodsPicDao.findGoodsPicByGoodsSn(goodsSn));
          goodsList.add(goods);
        }
        return goodsList;
      }

      //提交补货记录
      @Override
      @Transactional
      public void commitSupplyRecords(Long suppId, String sceneSn, List<SupplyRecord> records) throws Exception {
        Integer suppCount = 0;
        SupplementSumRec supplementSumRec = supplementSumRecDao.findSupplementSumRecordBySceneSn(suppId, sceneSn);
        
        if (supplementSumRec == null)
          throw new Exception("未开始补货");
        for (SupplyRecord record : records) {
          SupplementList supplementList = supplementListDao.find(record.getSupplementId());
          SupplementRecord supplementRecord = new SupplementRecord();
          supplementRecord.setChannel(supplementList.getChannel());
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
            throw new Exception("补过数超过待补数");
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
        List<SupplementRecord> records = supplementRecordDao.findRecordByCntrId(suppId, cntrId);
        for (SupplementRecord supplementRecord : records) {
          supplementRecord.setSuppPic(supplementPic);
        }
        Set<SupplementRecord> recordSet = new HashSet<>();
        recordSet.addAll(records);
        supplementPic.setSupplementRecords(recordSet);
        supplementPicDao.persist(supplementPic);
      }

      //完成补货
      @Override
      @Transactional
      public Object[] finishSupplyGoods(Long suppId, String sceneSn) {
        Object[] objs = supplementSumRecDao.findUnfinishedSupplyRecord(suppId, sceneSn);
        if (objs != null)
          return objs;
        SupplementSumRec supplementSumRec = supplementSumRecDao.findSupplementSumRecordBySceneSn(suppId, sceneSn);
        if (supplementSumRec == null) {
          return null;
        }
        supplementSumRec.setSuppEndTime(new Date());
        supplementSumRecDao.persist(supplementSumRec);
        KeeperRemindMsg msg = new KeeperRemindMsg();
        msg.setTitle("优享空间\""+supplementSumRec.getSceneName()+"\"补货通知");
        msg.setContent("优享空间\""+supplementSumRec.getSceneName()+"\"已完成补货,完成时间："
            +new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(supplementSumRec.getSuppEndTime())+"\n"
            +"负责的管家："+supplementSumRec.getSuppName()+"("+supplementSumRec.getSuppMobile()+")\n"
            +"补货情况：总待补货"+supplementSumRec.getWaitSuppTotalCount()+"件，实际补货"
            +supplementSumRec.getSuppTotalCount()+"件，仍缺货"+supplementSumRec.getLackCount()+"件。");
        msg.setType(RemindType.SUPPLY);
        keeperRemindMsgDao.persist(msg);
        return null;
      }
      
      //生成补货清单
      @Override
      public void createSupplyRecordList() {
        List<SupplementList> lists = new LinkedList<>();
        List<Object[]> supplyRecordObjs = containerChannelDao.getChannelSupplyInfo();
        for (Object[] supplyRecord : supplyRecordObjs) {
          SupplementList list = new SupplementList();
          list.setChannel(containerChannelDao.find((Long)supplyRecord[0]));
          list.setCntrId((Long)supplyRecord[1]);
          list.setCntrSn((String)supplyRecord[2]);
          list.setSuppId((Long)supplyRecord[3]);
          list.setGoodsSn((String)supplyRecord[4]);
          list.setGoodsName((String)supplyRecord[5]);
          list.setSceneId((Long)supplyRecord[6]);
          list.setSceneSn((String)supplyRecord[7]);
          list.setSceneName((String)supplyRecord[8]);
          list.setRemainCount((Integer)supplyRecord[9]);
          list.setWaitSupplyCount((Integer)supplyRecord[10]);
          lists.add(list);
        }
        supplementListDao.persist(lists);
      }
      
}