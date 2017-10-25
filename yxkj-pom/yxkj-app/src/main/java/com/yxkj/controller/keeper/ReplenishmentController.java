package com.yxkj.controller.keeper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.bean.WaitSupplyContainerGoods;
import com.yxkj.json.bean.WaitSupplyGoods;
import com.yxkj.json.bean.WaitSupplyGoodsDetails;
import com.yxkj.json.bean.WaitSupplyList;
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
	public @ResponseBody ResponseOne<List<Map<String, String>>> getWaitSupplySceneList(
	    @ApiParam(name = "请求参数(json)", value = "{userId:管家ID}",required = true)
	    @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
	  ResponseOne<List<Map<String, String>>> response = new ResponseOne<>();
	  List<Map<String, String>> sceneList = new LinkedList<>();
	  try{
	    sceneList = supplementListService.getWaitSupplySceneList(waitSupplyListRequest.getUserId());
	  }catch (Exception e) {
	    e.printStackTrace();
	    response.setCode(CommonAttributes.FAIL_COMMON);
	    response.setDesc(message("yxkj.request.fail"));
	    return response;
	  }
	  response.setCode(CommonAttributes.SUCCESS);
	  response.setDesc(message("yxkj.request.success"));
	  response.setMsg(sceneList);
	  return response;
	}
	
	@RequestMapping(value="/getWaitSupplyGoodsCategoryList", method=RequestMethod.POST)
	@ApiOperation(value = "获取待补商品类别", httpMethod = "POST", response = ResponseOne.class, notes = "获取待补商品类别")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<List<Map<String, Object>>> getWaitSupplyGoodsCategory(
        @ApiParam(name = "请求参数(json)", value = "{userId:管家ID}",required = true)
        @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
      ResponseOne<List<Map<String,Object>>> response = new ResponseOne<>();
      List<Map<String,Object>> goodsCategoryList = new LinkedList<>();
      try{
        goodsCategoryList = supplementListService.getWaitSupplyGoodsCategoryList(waitSupplyListRequest.getUserId());
      }catch (Exception e) {
        e.printStackTrace();
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(message("yxkj.request.fail"));
        return response;
      }
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      response.setMsg(goodsCategoryList);
      return response;
    }
	
	@RequestMapping(value="/getWaitSupplyGoodList", method=RequestMethod.POST)
    @ApiOperation(value = "获取待补商品清单", httpMethod = "POST", response = ResponseOne.class, notes = "获取待补商品清单")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<List<WaitSupplyGoods>> getWaitSupplyGoodList(
            @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,sceneSn:优享空间编号,cateId:商品类型Id,pageNo:页码,pageSize:页记录数}",required = true)
            @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
        ResponseOne<List<WaitSupplyGoods>> response = new ResponseOne<>();
        List<WaitSupplyGoods> waitSupplyGoodsList = null;
        try{
          waitSupplyGoodsList = supplementListService.getWaitSupplyGoodList(waitSupplyListRequest.getUserId(),
              waitSupplyListRequest.getSceneSn(), waitSupplyListRequest.getCateId(), waitSupplyListRequest.getPageNo(),
              Integer.valueOf(waitSupplyListRequest.getPageSize()).intValue());
        }catch (Exception e) {
          e.printStackTrace();
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.fail"));
          return response;
        }
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
        response.setMsg(waitSupplyGoodsList);
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
	
	@RequestMapping(value="/getWaitSupplyContainerGoodList", method=RequestMethod.POST)
    @ApiOperation(value = "获取货柜待补商品清单", httpMethod = "POST", response = ResponseOne.class, notes = "获取货柜待补商品清单")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<List<WaitSupplyContainerGoods>> getWaitSupplyContainerGoodList(
            @ApiParam(name = "请求参数(json)", value = "{userId:管家ID,cntrId:货柜ID,pageNo:页码,pageSize:页记录数}",required = true)
            @RequestBody WaitSupplyListRequest waitSupplyListRequest) {
        ResponseOne<List<WaitSupplyContainerGoods>> response = new ResponseOne<>();
        List<WaitSupplyContainerGoods> waitSupplyContainerGoodsList = new LinkedList<>();
        try{
          waitSupplyContainerGoodsList = supplementListService.getWaitSupplyContainerGoods(waitSupplyListRequest.getUserId(),
              waitSupplyListRequest.getCntrId(), waitSupplyListRequest.getPageNo(),
              Integer.valueOf(waitSupplyListRequest.getPageSize()).intValue());
        }catch (Exception e) {
          e.printStackTrace();
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.fail"));
          return response;
        }
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
        response.setMsg(waitSupplyContainerGoodsList);
        return response;
    }
	
	@RequestMapping(value="/commitSupplementRecord", method=RequestMethod.POST)
    @ApiOperation(value = "提交补货记录", httpMethod = "POST", response = ResponseOne.class, notes = "提交补货记录")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<String> commitSupplementRecord(
            @ApiParam(name = "请求参数(json)", value = "",required = true)
            @RequestBody String request) {
	    
	    return null;
	}
    
}
