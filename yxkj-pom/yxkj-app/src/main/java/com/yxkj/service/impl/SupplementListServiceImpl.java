package com.yxkj.service.impl; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementList;
import com.yxkj.dao.SupplementListDao;
import com.yxkj.service.SupplementListService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.base.WaitSupplyContainerGoods;
import com.yxkj.json.base.WaitSupplyGoods;
import com.yxkj.json.base.WaitSupplyGoodsDetails;
import com.yxkj.json.base.WaitSupplyList;

@Service("supplementListServiceImpl")
public class SupplementListServiceImpl extends BaseServiceImpl<SupplementList,Long> implements SupplementListService {

      @Resource(name="supplementListDaoImpl")
      private SupplementListDao supplementListDao;
      
      @Resource(name="supplementListDaoImpl")
      public void setBaseDao(SupplementListDao supplementListDao) {
         super.setBaseDao(supplementListDao);
      }
      
      //获取待补货清单
      public WaitSupplyList getWaitSupplyListBySuppId(Long suppId, String pageNo, int pageSize) {
        
        WaitSupplyList waitSupplyList = new WaitSupplyList();
        
        int waitSupplySumCount = 0;
        List<WaitSupplyList.WaitSupplyScene> scenesList = new LinkedList<>();
        List<Object[]> sceneList = supplementListDao.findWaitSupplyScene(suppId, Integer.valueOf(pageNo).intValue(), pageSize);
        for(Object[] sceneObjs : sceneList) {
          String sceneSn = (String)sceneObjs[0];
          String sceneName = (String)sceneObjs[1];
          WaitSupplyList.WaitSupplyScene scene = waitSupplyList.new WaitSupplyScene(sceneSn, sceneName);
          List<WaitSupplyList.WaitSupplyScene.VendingContainerGroup> groupList = new LinkedList<>();
          
          List<Object[]> centralContainerList = supplementListDao.findCentralVendingContainer(sceneSn);
          for(Object[] centralContainer : centralContainerList){
            WaitSupplyList.WaitSupplyScene.VendingContainerGroup group = scene.new VendingContainerGroup();
            List<WaitSupplyList.WaitSupplyScene.VendingContainerGroup.WaitSupplyVendingContainer> containerList = new LinkedList<>();
            
            //获取中控柜
            WaitSupplyList.WaitSupplyScene.VendingContainerGroup.WaitSupplyVendingContainer central = group
                .new WaitSupplyVendingContainer((Long)centralContainer[0], (String)centralContainer[1]);
            central.setCentral(true);
            containerList.add(central);
            
            //获取子货柜
            List<Object[]> childContainerList = supplementListDao.findChildrenVendingContainer((Long)centralContainer[0]);
            for(Object[] childContainer : childContainerList) {
              WaitSupplyList.WaitSupplyScene.VendingContainerGroup.WaitSupplyVendingContainer child = group
                  .new WaitSupplyVendingContainer((Long)childContainer[0], (String)childContainer[1]);
              int count = 0;
              try{
                  count = supplementListDao.findWaitSupplyCount((Long)childContainer[0]);
              }catch (Exception e) {}
              child.setWaitSupplyCount(count);
              waitSupplySumCount += count;
              containerList.add(child);
            }
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
      public Map<String, String> getWaitSupplySceneList(Long suppId) {
        Map<String, String> sceneMap = new HashMap<>();
        
        List<Object[]> waitSupplySceneList = supplementListDao.findWaitSupplySceneList(suppId);
        for(Object[] sceneObjs : waitSupplySceneList) {
          sceneMap.put((String)sceneObjs[0], (String)sceneObjs[1]);
        }
        return sceneMap;
      }
      
      @Override
      public List<WaitSupplyGoods> getWaitSupplyGoodList(Long suppId, String sceneSn, String cateName,
          String pageNo, int pageSize) {
        List<WaitSupplyGoods> waitSupplyGoodList = new LinkedList<>();
        
        List<Object[]> goodsList = supplementListDao.findWaitSupplyGoodsList(suppId, sceneSn, cateName,
            Integer.valueOf(pageNo), pageSize);
        for(Object[] goodsObj : goodsList) {
          WaitSupplyGoods waitSupplyGoods = new WaitSupplyGoods((String)goodsObj[0], (String)goodsObj[1], (Integer)goodsObj[2]);
          waitSupplyGoods.setGoodsPic((String)supplementListDao.findGoodsPicByGoodsSn((String)goodsObj[0]));
          waitSupplyGoodList.add(waitSupplyGoods);
        }
        return waitSupplyGoodList;
      }
      @Override
      public List<String> getWaitSupplyGoodsCategoryList(Long  suppId) {
        List<String> waitSupplyGoodsCategoryList = new LinkedList<>();
        
        List<Object> categoryList = supplementListDao.findWaitSupplyGoodsCategoryList(suppId);
        for (Object category : categoryList) {
          waitSupplyGoodsCategoryList.add((String)category);
        }
        return waitSupplyGoodsCategoryList;
      }

      @Override
      public WaitSupplyGoodsDetails getWaitSupplyGoodsDetails(Long suppId, String goodsSn) {
        Integer sumCount = 0;
        Map<String, Integer> sceneCountMap = new HashMap<>();
        List<Object[]> goodsDetailsList = supplementListDao.findWaitSupplyGoodsDetails(suppId, goodsSn);
        String goodsName = (String)goodsDetailsList.get(0)[0];
        for (Object[] goodsDetails : goodsDetailsList) {
          Integer waitSupplyCount = (Integer)goodsDetails[2];
          sceneCountMap.put((String)goodsDetails[1], waitSupplyCount);
          sumCount += waitSupplyCount;
        }
        return new WaitSupplyGoodsDetails(goodsSn, goodsName, sceneCountMap, sumCount);
      }

      @Override
      public List<WaitSupplyContainerGoods> getWaitSupplyContainerGoods(Long suppId, Long cntrId,
          String pageNo, int pageSize) {
        List<WaitSupplyContainerGoods> waitSupplyContainerGoodsList = new LinkedList<>();
        List<Object[]> containerGoodsList = supplementListDao.getWaitSupplyContainerGoods(suppId, cntrId, 
            Integer.valueOf(pageNo), pageSize);
        for(Object[] goodsObj : containerGoodsList) {
          WaitSupplyContainerGoods waitSupplyContainerGoods = new WaitSupplyContainerGoods((String)goodsObj[0],
              (String)goodsObj[1], (String)goodsObj[2],(Integer)goodsObj[3]);
          waitSupplyContainerGoods.setGoodsPic((String)supplementListDao.findGoodsPicByGoodsSn((String)goodsObj[0]));
          waitSupplyContainerGoodsList.add(waitSupplyContainerGoods);
        }
        return waitSupplyContainerGoodsList;
      }

}