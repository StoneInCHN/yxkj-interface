package com.yxkj.controller.keeper;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.bean.WaitSupplyContainerGoods;
import com.yxkj.json.bean.WaitSupplyGoods;
import com.yxkj.json.bean.WaitSupplyGoodsDetails;
import com.yxkj.json.bean.WaitSupplyList;
import com.yxkj.json.request.SupplementRecordRequest;
import com.yxkj.json.request.WaitSupplyListRequest;
import com.yxkj.service.SupplementListService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("/keeper")
public class ReplenishmentController extends MobileBaseController {

	@Resource(name="supplementListServiceImpl")
	private SupplementListService supplementListService;
	
	
	@RequestMapping(value="/getWaitSupplyState", method=RequestMethod.POST)
	@ApiOperation(value = "获取货柜待补情况", httpMethod = "POST", response = ResponseOne.class, notes = "获取货柜待补情况")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
	public @ResponseBody ResponseOne<WaitSupplyList> getWaitSupplyList(
	        @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,pageNo:页码,pageSize:页记录数}",required = true)
	        @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
	    ResponseOne<WaitSupplyList> response = new ResponseOne<>();
	    WaitSupplyList waitSupplyList = null;
	    try{
	      waitSupplyList = supplementListService.getWaitSupplyListBySuppId(waitSupplyListRequest.getUserId(),
	          waitSupplyListRequest.getPageNo(), Integer.valueOf(waitSupplyListRequest.getPageSize()).intValue());
	    }catch (Exception e) {
	      e.printStackTrace();
	      response.setCode(CommonAttributes.FAIL_COMMON);
	      response.setDesc(message("yxkj.request.fail"));
	      return response;
        }
	    response.setCode(CommonAttributes.SUCCESS);
	    response.setDesc(message("yxkj.request.success"));
	    response.setMsg(waitSupplyList);
		return response;
	}
	
	@RequestMapping(value="/getWaitSupplySceneList", method=RequestMethod.POST)
	@ApiOperation(value = "获取待补优享空间", httpMethod = "POST", response = ResponseOne.class, notes = "获取待补优享空间")
	@ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
	public @ResponseBody ResponseOne<Map<String, Object>> getWaitSupplySceneList(
	    @ApiParam(name = "请求参数(json)", value = "{userId:管家ID}",required = true)
	    @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
	  ResponseOne<Map<String, Object>> response = new ResponseOne<>();
	  Map<String, Object> sceneMap = new HashMap<>();
	  List<Map<String, String>> sceneList = new LinkedList<>();
	  try{
	    sceneList = supplementListService.getWaitSupplySceneList(waitSupplyListRequest.getUserId());
	    sceneMap.put("groups", sceneList);
	  }catch (Exception e) {
	    e.printStackTrace();
	    response.setCode(CommonAttributes.FAIL_COMMON);
	    response.setDesc(message("yxkj.request.fail"));
	    return response;
	  }
	  response.setCode(CommonAttributes.SUCCESS);
	  response.setDesc(message("yxkj.request.success"));
	  response.setMsg(sceneMap);
	  return response;
	}
	
	@RequestMapping(value="/getWaitSupplyGoodsCategoryList", method=RequestMethod.POST)
	@ApiOperation(value = "获取待补商品类别", httpMethod = "POST", response = ResponseOne.class, notes = "获取待补商品类别")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<Map<String, Object>> getWaitSupplyGoodsCategory(
        @ApiParam(name = "请求参数(json)", value = "{userId:管家ID}",required = true)
        @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
      ResponseOne<Map<String,Object>> response = new ResponseOne<>();
      Map<String, Object> cateMap = new HashMap<>();
      List<Map<String,Object>> goodsCategoryList = new LinkedList<>();
      try{
        goodsCategoryList = supplementListService.getWaitSupplyGoodsCategoryList(waitSupplyListRequest.getUserId());
        cateMap.put("groups", goodsCategoryList);
      }catch (Exception e) {
        e.printStackTrace();
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(message("yxkj.request.fail"));
        return response;
      }
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      response.setMsg(cateMap);
      return response;
    }
	
	@RequestMapping(value="/getWaitSupplyGoodsList", method=RequestMethod.POST)
    @ApiOperation(value = "获取待补商品清单", httpMethod = "POST", response = ResponseOne.class, notes = "获取待补商品清单")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<Map<String, Object>> getWaitSupplyGoodList(
            @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,sceneSn:优享空间编号,cateId:商品类型Id,pageNo:页码,pageSize:页记录数}",required = true)
            @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
        ResponseOne<Map<String, Object>> response = new ResponseOne<>();
        Map<String, Object> goodsMap = new HashMap<>();
        List<WaitSupplyGoods> waitSupplyGoodsList = null;
        try{
          waitSupplyGoodsList = supplementListService.getWaitSupplyGoodList(waitSupplyListRequest.getUserId(),
              waitSupplyListRequest.getSceneSn(), waitSupplyListRequest.getCateId(), waitSupplyListRequest.getPageNo(),
              Integer.valueOf(waitSupplyListRequest.getPageSize()).intValue());
          goodsMap.put("groups", waitSupplyGoodsList);
        }catch (Exception e) {
          e.printStackTrace();
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.fail"));
          return response;
        }
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
        response.setMsg(goodsMap);
        return response;
    }
	
	@RequestMapping(value="/getWaitSupplyGoodsDetails", method=RequestMethod.POST)
	@ApiOperation(value = "获取待补商品详情", httpMethod = "POST", response = ResponseOne.class, notes = "获取待补商品详情")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<WaitSupplyGoodsDetails> getWaitSupplyGoodsDetails(
            @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,goodsSn:商品编号}",required = true)
            @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
	    ResponseOne<WaitSupplyGoodsDetails> response = new ResponseOne<>();
	    WaitSupplyGoodsDetails waitSupplyGoodsDetails;
  	    try {
  	      waitSupplyGoodsDetails = supplementListService.getWaitSupplyGoodsDetails(waitSupplyListRequest.getUserId(),
  	          waitSupplyListRequest.getGoodsSn());
        } catch (Exception e) {
          e.printStackTrace();
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.fail"));
          return response;
        }
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
        response.setMsg(waitSupplyGoodsDetails);
        return response;
	}
	
//	@RequestMapping(value="/startSupplyGoods", method=RequestMethod.POST)
//    @ApiOperation(value = "获取货柜待补商品清单", httpMethod = "POST", response = ResponseOne.class, notes = "获取货柜待补商品清单")
//    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
//    public @ResponseBody ResponseOne<Map<String, Object>> startSupplyGoods(
//            @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,sceneId}", required = true)
//            @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
//	}
//	
	@RequestMapping(value="/getWaitSupplyContainerGoodsList", method=RequestMethod.POST)
    @ApiOperation(value = "获取货柜待补商品清单", httpMethod = "POST", response = ResponseOne.class, notes = "获取货柜待补商品清单")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<Map<String, Object>> getWaitSupplyContainerGoodList(
            @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,cntrId:货柜ID,pageNo:页码,pageSize:页记录数}", required = true)
            @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
        ResponseOne<Map<String, Object>> response = new ResponseOne<>();
        Map<String, Object> goodsMap = new HashMap<>();
        List<WaitSupplyContainerGoods> waitSupplyContainerGoodsList = new LinkedList<>();
        try{
          waitSupplyContainerGoodsList = supplementListService.getWaitSupplyContainerGoods(waitSupplyListRequest.getUserId(),
              waitSupplyListRequest.getCntrId(), waitSupplyListRequest.getPageNo(),
              Integer.valueOf(waitSupplyListRequest.getPageSize()).intValue());
          goodsMap.put("groups", waitSupplyContainerGoodsList);
        }catch (Exception e) {
          e.printStackTrace();
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.fail"));
          return response;
        }
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
        response.setMsg(goodsMap);
        return response;
    }
	
	@RequestMapping(value="/getContainerGoodsList", method=RequestMethod.POST)
    @ApiOperation(value = "获取货柜全部商品清单", httpMethod = "POST", response = ResponseOne.class, notes = "获取货柜待补商品清单")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<Map<String, Object>> getContainerGoodsList(
            @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,cntrId:货柜ID,pageNo:页码,pageSize:页记录数}", required = true)
            @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
        ResponseOne<Map<String, Object>> response = new ResponseOne<>();
        Map<String, Object> goodsMap = new HashMap<>();
        List<WaitSupplyContainerGoods> waitSupplyContainerGoodsList = new LinkedList<>();
        try{
          
          goodsMap.put("groups", waitSupplyContainerGoodsList);
        }catch (Exception e) {
          e.printStackTrace();
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.fail"));
          return response;
        }
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
        response.setMsg(goodsMap);
        return response;
    }
	
	@RequestMapping(value="/commitSupplementRecord", method=RequestMethod.POST)
    @ApiOperation(value = "提交补货记录", httpMethod = "POST", response = ResponseOne.class, notes = "提交补货记录")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse commitSupplementRecord(
            @ApiParam(name = "请求参数(json)", value = "{userId:管家Id, sceneSn:优享空间编号,"
                + "supplementRecords: [{supplementId:待补记录id, supplyCount:实际补货数}]}", required = true)
            @RequestBody SupplementRecordRequest request) {
	    BaseResponse response = new BaseResponse();
	    try {
	      supplementListService.commitSupplyRecords(request.getUserId(),
	          request.getSceneSn(), request.getSupplementRecords());
        } catch (Exception e) {
          e.printStackTrace();
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.failed"));
          return response;
        }
	    response.setCode(CommonAttributes.SUCCESS);
	    response.setDesc(message("yxkj.request.success"));
	    return response;
	}
	
	@RequestMapping(value="/uploadSupplementPic", method=RequestMethod.POST)
    @ApiOperation(value = "上传补货照片", httpMethod = "POST", response = ResponseOne.class, notes = "上传补货照片")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse uploadSupplementPic(
            @ApiParam(name = "请求参数(json)", value = "{userId:管家Id, cntrId:货柜id}", required = true)
            @RequestBody SupplementRecordRequest req, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
	    BaseResponse response = new BaseResponse();
        List<String> picFileType = Arrays.asList("jpg", "gif", "jpeg", "png");
        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
        if (file != null) {
          String fileType = file.getOriginalFilename().split("\\.")[1];
          if(picFileType.contains(fileType)) {
            String picFileName = UUID.randomUUID().toString() + "." +fileType;
            try {
              file.transferTo(new File(filePath+picFileName));
              supplementListService.uploadSupplementPic(req.getUserId(), req.getCntrId(), picFileName);
            } catch (Exception e) {
              e.printStackTrace();
              response.setCode(CommonAttributes.FAIL_COMMON);
              response.setDesc(message("yxkj.request.failed"));
              return response;
            }
            response.setCode(CommonAttributes.SUCCESS);
            response.setDesc(message("yxkj.request.success"));
            return response;
          }
        }
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(message("yxkj.request.failed"));
        return response;
	}
	
//	@RequestMapping(value="/getSupplementSumRecord", method=RequestMethod.POST)
//    @ApiOperation(value = "查看总补货记录", httpMethod = "POST", response = ResponseOne.class, notes = "查看总补货记录")
//    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
//    public @ResponseBody ResponseOne<Map<String, Object>> getSupplementSumRecord() {
//        ResponseOne<Map<String, Object>> response = new ResponseOne<>();
//        Map<String, Object> supplyRecordMap = new HashMap<>(); 
//        List<SupplementSumRecord> records = new LinkedList<>();
//        try {
//          
//          supplyRecordMap.put("records", records);
//        } catch (Exception e) {
//          e.printStackTrace();
//          response.setCode(CommonAttributes.FAIL_COMMON);
//          response.setDesc(message("yxkj.request.failed"));
//          return response;
//        }
//        response.setCode(CommonAttributes.SUCCESS);
//        response.setDesc(message("yxkj.request.success"));
//        response.setMsg(supplyRecordMap);
//        return response;
//    }
	
//	@RequestMapping(value="/getSupplementRecordDetails", method=RequestMethod.POST)
//    @ApiOperation(value = "查看补货记录详情", httpMethod = "POST", response = ResponseOne.class, notes = "查看补货记录详情")
//    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
//    public @ResponseBody ResponseOne<Map<String, Object>> getSupplementRecordDetails(
//            @ApiParam(name = "请求参数(json)", value = "{userId:管家Id, sceneSn:优享空间编号}")
//            @RequestBody SupplementRecordRequest request) {
//        ResponseOne<Map<String, Object>> response = new ResponseOne<>();
//        Map<String, Object> supplyRecordMap = new HashMap<>(); 
//        try {
//          
//        } catch (Exception e) {
//          e.printStackTrace();
//          response.setCode(CommonAttributes.FAIL_COMMON);
//          response.setDesc(message("yxkj.request.failed"));
//          return response;
//        }
//        response.setCode(CommonAttributes.SUCCESS);
//        response.setDesc(message("yxkj.request.success"));
//        return response;
//    }
	
}
