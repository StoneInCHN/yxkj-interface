package com.yxkj.controller.keeper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yxkj.aspect.UserValidCheck;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.commonenum.CommonEnum.ImageType;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.bean.DailySumSupplementRecord;
import com.yxkj.json.bean.SceneSupplementRecord;
import com.yxkj.json.bean.WaitSupplyContainerGoods;
import com.yxkj.json.bean.WaitSupplyGoods;
import com.yxkj.json.bean.WaitSupplyGoodsDetails;
import com.yxkj.json.bean.WaitSupplyList;
import com.yxkj.json.request.SupplementRecordRequest;
import com.yxkj.json.request.WaitSupplyListRequest;
import com.yxkj.service.FileService;
import com.yxkj.service.SupplementListService;
import com.yxkj.service.SupplementRecordService;
import com.yxkj.service.SupplementSumRecService;

@Controller
@RequestMapping("/keeper")
public class ReplenishmentController extends MobileBaseController {

  @Resource(name = "supplementListServiceImpl")
  private SupplementListService supplementListService;

  @Resource(name = "supplementRecordServiceImpl")
  private SupplementRecordService supplementRecordService;

  @Resource(name = "supplementSumRecServiceImpl")
  private SupplementSumRecService supplementSumRecService;

  @Resource(name = "fileServiceImpl")
  private FileService fileService;

  @UserValidCheck
  @RequestMapping(value = "/getWaitSupplyState", method = RequestMethod.POST)
  @ApiOperation(value = "获取货柜待补情况", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取货柜待补情况")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<WaitSupplyList> getWaitSupplyList(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,pageNo:页码,pageSize:页记录数}",
          required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<WaitSupplyList> response = new ResponseOne<>();
    WaitSupplyList waitSupplyList = null;
    try {
      waitSupplyList = supplementListService.getWaitSupplyListBySuppId(request.getUserId(),
          request.getPageNo(), Integer.valueOf(request.getPageSize()).intValue());
    } catch (Exception e) {
      e.printStackTrace();
      LogUtil
          .debug(this.getClass(), "getWaitSupplyState","获取货柜待补情况失败,userId: %s",
              request.getUserId().toString());
      
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.failed").toString());
      return response;
    }
    LogUtil
        .debug(this.getClass(), "getWaitSupplyState", "获取货柜待补情况成功,userId: %s",
            request.getUserId());
      
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    response.setMsg(waitSupplyList);
    return response;
  }
  
  @UserValidCheck
  @RequestMapping(value = "/getWaitSupplySceneList", method = RequestMethod.POST)
  @ApiOperation(value = "获取待补优享空间", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取待补优享空间")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getWaitSupplySceneList(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家ID}", required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> sceneMap = new HashMap<>();
    List<Map<String, String>> sceneList = 
        supplementListService.getWaitSupplySceneList(request.getUserId());
    sceneMap.put("groups", sceneList);
    LogUtil
        .debug(this.getClass(), "getWaitSupplySceneList", "获取待补优享空间成功,userId: %s",
            request.getUserId().toString());
    
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    response.setMsg(sceneMap);
    return response;
  }

  @UserValidCheck
  @RequestMapping(value = "/getWaitSupplyGoodsCategoryList", method = RequestMethod.POST)
  @ApiOperation(value = "获取待补商品类别", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取待补商品类别")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getWaitSupplyGoodsCategory(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家ID}", required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> cateMap = new HashMap<>();
    List<Map<String, Object>> goodsCategoryList = 
        supplementListService.getWaitSupplyGoodsCategoryList(request.getUserId());
    cateMap.put("groups", goodsCategoryList);
    LogUtil
        .debug(this.getClass(), "getWaitSupplyGoodsCategoryList", "获取待补商品类别成功,userId: %s",
            request.getUserId().toString());
    
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    response.setMsg(cateMap);
    return response;
  }

  @UserValidCheck
  @RequestMapping(value = "/getWaitSupplyGoodsList", method = RequestMethod.POST)
  @ApiOperation(value = "获取待补商品清单", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取待补商品清单")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getWaitSupplyGoodList(
      @ApiParam(name = "请求参数(json)",
          value = "{userId:管家ID,sceneSn:优享空间编号,cateId:商品类型Id,pageNo:页码,pageSize:页记录数}",
          required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> goodsMap = new HashMap<>();
    List<WaitSupplyGoods> waitSupplyGoodsList =
        supplementListService.getWaitSupplyGoodList(request.getUserId(),
            request.getSceneSn(), request.getCateId(), request.getPageNo(),
            Integer.valueOf(request.getPageSize()).intValue());
    goodsMap.put("groups", waitSupplyGoodsList);
    LogUtil
        .debug(this.getClass(), "getWaitSupplyGoodsList", "获取待补商品清单成功,userId: %s, sceneSn: %s, cateId: %s",
            request.getUserId().toString(), request.getSceneSn(),
            request.getCateId().toString());
    
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    response.setMsg(goodsMap);
    return response;
  }

  @UserValidCheck
  @RequestMapping(value = "/getWaitSupplyGoodsDetails", method = RequestMethod.POST)
  @ApiOperation(value = "获取待补商品详情", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取待补商品详情")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<WaitSupplyGoodsDetails> getWaitSupplyGoodsDetails(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,goodsSn:商品编号}", required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<WaitSupplyGoodsDetails> response = new ResponseOne<>();
    WaitSupplyGoodsDetails waitSupplyGoodsDetails =
        supplementListService.getWaitSupplyGoodsDetails(request.getUserId(),
            request.getGoodsSn());
    LogUtil
        .debug(this.getClass(), "getWaitSupplyGoodsDetails", "获取待补商品详情成功,userId: %s, goodsSn: %s",
            request.getUserId().toString(), request.getGoodsSn());
    
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    response.setMsg(waitSupplyGoodsDetails);
    return response;
  }

  @UserValidCheck
  @RequestMapping(value = "/startSupplyGoods", method = RequestMethod.POST)
  @ApiOperation(value = "开始补货", httpMethod = "POST", response = ResponseOne.class, notes = "开始补货")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> startSupplyGoods(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,sceneSn:优享空间编号}", required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> map = new HashMap<>();
    Object[] sceneObjs =
        supplementListService.startSupplyGoods(request.getUserId(),
            request.getSceneSn());
    if (sceneObjs != null) {
      map.put("sceneSn", (String) sceneObjs[0]);
      map.put("sceneName", (String) sceneObjs[1]);
      LogUtil
      .debug(this.getClass(), "startSupplyGoods", "开始补货失败,userId: %s, sceneSn: %s",
          request.getUserId().toString(), request.getSceneSn());
      
    }
    LogUtil
        .debug(this.getClass(), "startSupplyGoods", "开始补货成功,userId: %s, sceneSn: %s",
            request.getUserId().toString(), request.getSceneSn());
    
    response.setMsg(map);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }
  
  @UserValidCheck
  @RequestMapping(value = "/getWaitSupplyContainerGoodsList", method = RequestMethod.POST)
  @ApiOperation(value = "获取货柜待补商品清单", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取货柜待补商品清单")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getWaitSupplyContainerGoodList(@ApiParam(
      name = "请求参数(json)", value = "{userId:管家ID,cntrId:货柜ID,pageNo:页码,pageSize:页记录数}",
      required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> goodsMap = new HashMap<>();
    List<WaitSupplyContainerGoods> waitSupplyContainerGoodsList = 
        supplementListService.getWaitSupplyContainerGoods(request.getUserId(),
            request.getCntrId(), request.getPageNo(), Integer.valueOf(request.getPageSize()).intValue());
    goodsMap.put("groups", waitSupplyContainerGoodsList);
    LogUtil
        .debug(this.getClass(), "getWaitSupplyContainerGoodsList", "获取货柜待补商品清单成功, userId: %s, cntrId: %s",
            request.getUserId().toString(), request.getCntrId().toString());
    
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    response.setMsg(goodsMap);
    return response;
  }

  @UserValidCheck
  @RequestMapping(value = "/getContainerGoodsList", method = RequestMethod.POST)
  @ApiOperation(value = "获取货柜全部商品清单", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取货柜待补商品清单")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getContainerGoodsList(@ApiParam(
      name = "请求参数(json)", value = "{userId:管家ID,cntrId:货柜ID,pageNo:页码,pageSize:页记录数}",
      required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> goodsMap = new HashMap<>();
    List<WaitSupplyContainerGoods> waitSupplyContainerGoodsList =
        supplementListService.getContainerGoodsList(request.getCntrId(),
            request.getPageNo(), Integer.valueOf(request.getPageSize()).intValue());
    goodsMap.put("groups", waitSupplyContainerGoodsList);
    LogUtil
        .debug(this.getClass(), "getContainerGoodsList", "获取货柜全部商品清单成功, userId: %s, cntrId: %s",
            request.getUserId().toString(), request.getCntrId().toString());
    
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    response.setMsg(goodsMap);
    return response;
  }

  @UserValidCheck
  @RequestMapping(value = "/commitSupplementRecord", method = RequestMethod.POST)
  @ApiOperation(value = "提交补货记录", httpMethod = "POST", response = ResponseOne.class,
      notes = "提交补货记录")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse commitSupplementRecord(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家Id, sceneSn:优享空间编号,"
          + "supplementRecords: [{supplementId:待补记录id, supplyCount:实际补货数}]}", required = true) @RequestBody SupplementRecordRequest request) {
    BaseResponse response = new BaseResponse();
    try {
      supplementListService.commitSupplyRecords(request.getUserId(), request.getSceneSn(),
          request.getSupplementRecords());
    } catch (Exception e) {
      e.printStackTrace();
      LogUtil
          .debug(this.getClass(), "commitSupplementRecord", "提交补货记录失败, userId: %s, sceneSn: %s ,supplyRecords: %s",
              request.getUserId().toString(), request.getSceneSn(),
              request.getSupplementRecords().toString());
      
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.failed"));
      return response;
    }
    LogUtil
    .debug(this.getClass(), "commitSupplementRecord", "提交补货记录成功, userId: %s, sceneSn: %s ,supplyRecords: %s",
        request.getUserId().toString(), request.getSceneSn(),
        request.getSupplementRecords().toString());

    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }

  @UserValidCheck
  @RequestMapping(value = "/uploadSupplementPic", method = RequestMethod.POST)
  @ApiOperation(value = "上传补货照片", httpMethod = "POST", response = ResponseOne.class,
      notes = "上传补货照片")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse uploadSupplementPic(@ApiParam(name = "请求参数(json)",
      value = "{userId:管家Id, cntrId:货柜id}", required = true) SupplementRecordRequest req,
      HttpServletRequest request) {
    BaseResponse response = new BaseResponse();
    try {
      MultipartFile suppPic = req.getSuppPic();
      String picUrl = fileService.saveImage(suppPic, ImageType.KEEPER_SUPP_IMG);
      supplementListService.uploadSupplementPic(req.getUserId(), req.getCntrId(), picUrl);
      LogUtil.debug(this.getClass(), "uploadSupplementPic", "上传补货照片成功");
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      return response;
    } catch (Exception e) {
      LogUtil.debug(this.getClass(), "uploadSupplementPic", "上传补货照片失败");
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.failed"));
      return response;
    }
  }

  @UserValidCheck
  @RequestMapping(value = "/finishSupplyGoods", method = RequestMethod.POST)
  @ApiOperation(value = "完成补货", httpMethod = "POST", response = ResponseOne.class, notes = "完成补货")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> finishSupplyGoods(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,sceneSn:优享空间编号}", required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> map = new HashMap<>();
    Object[] sceneObjs = supplementListService.finishSupplyGoods(request.getUserId(),
            request.getSceneSn());
    if (sceneObjs != null) {
      map.put("sceneSn", (String) sceneObjs[0]);
      map.put("sceneName", (String) sceneObjs[1]);LogUtil
      .debug(this.getClass(), "startSupplyGoods", "开始补货失败,userId: %s, sceneSn: %s",
          request.getUserId().toString(), request.getSceneSn());
    }
    LogUtil
        .debug(this.getClass(), "startSupplyGoods", "开始补货成功,userId: %s, sceneSn: %s",
            request.getUserId().toString(), request.getSceneSn());
    
    response.setMsg(map);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }
  
  @UserValidCheck
  @RequestMapping(value = "/getSupplementSumRecord", method = RequestMethod.POST)
  @ApiOperation(value = "查看总补货记录", httpMethod = "POST", response = ResponseOne.class,
      notes = "查看总补货记录")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getSupplementSumRecord(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,pageNo:页码,pageSize:页记录数",
          required = true) @RequestBody WaitSupplyListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> supplyRecordMap = new HashMap<>();
    List<DailySumSupplementRecord> records =supplementSumRecService.findSupplySumRecord(request.getUserId(),
        request.getPageNo(), Integer.valueOf(request.getPageSize()).intValue());
    supplyRecordMap.put("groups", records);
    LogUtil
        .debug(this.getClass(), "getSupplementSumRecord", "查看总补货记录成功, userId: %s",
            request.getUserId().toString());

    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    response.setMsg(supplyRecordMap);
    return response;
  }

  @UserValidCheck
  @RequestMapping(value = "/getSupplementRecordDetails", method = RequestMethod.POST)
  @ApiOperation(value = "查看补货记录详情", httpMethod = "POST", response = ResponseOne.class,
      notes = "查看补货记录详情")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getSupplementRecordDetails(
      @ApiParam(name = "请求参数(json)", value = "{userId:管家Id, sceneSn:优享空间编号}") @RequestBody SupplementRecordRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<>();
    Map<String, Object> supplyRecordMap = new HashMap<>();
    List<SceneSupplementRecord> recordlist =
        supplementRecordService.getSupplementRecordDetails(request.getUserId(),
            request.getSceneSn());
    supplyRecordMap.put("groups", recordlist);
    LogUtil
        .debug(this.getClass(), "getSupplementRecordDetails", "查看补货记录详情成功, userId: %s, sceneSn: %s",
            request.getUserId().toString(), request.getSceneSn());
    
    response.setMsg(supplyRecordMap);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }
  
  @ResponseBody
  @RequestMapping(value="/createSupplyList", method = RequestMethod.POST)
  @ApiOperation(value = "创建补货清单", httpMethod = "POST", response = ResponseOne.class,
  notes = "创建补货清单")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public BaseResponse createSupplyList() {
    BaseResponse response = new BaseResponse();
    try {
      supplementListService.createSupplyRecordList();
    } catch (Exception e) {
      e.printStackTrace();
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setCode(message("yxkj.request.failed"));
      return response;
    }
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  };
  
}
