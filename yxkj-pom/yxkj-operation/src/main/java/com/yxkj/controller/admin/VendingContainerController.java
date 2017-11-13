package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.Scene;
import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.bean.GoodsData;
import com.yxkj.json.admin.bean.VendingContainerData;
import com.yxkj.json.admin.request.GoodsRequest;
import com.yxkj.json.admin.request.VendingContainerRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.SceneService;
import com.yxkj.service.VendingContainerService;
import com.yxkj.utils.FieldFilterUtils;

/**
 * 货柜管理 - Controller
 * @author luzhang
 */
@Controller("vendingContainerController")
@RequestMapping("/admin/vendingContainer")
@Api(value = "(货柜后台)货柜管理", description = "货柜管理")
public class VendingContainerController extends BaseController {

  @Resource(name = "sceneServiceImpl")
  private SceneService sceneService;
  
  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;
  
  /**
   * 单个场景的货柜列表
   * @param request
   * @return
   */
  @RequestMapping(value = "/vendingContainerList", method = RequestMethod.POST)
  @ApiOperation(value = "单个场景的货柜列表", httpMethod = "POST", response = ResponseOne.class, notes = "单个场景的货柜列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> vendingContainerList(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:场景Id", required = true)
  		@RequestBody BaseRequest request) {
	  	ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
      	if (request.getId() != null) {
      		Scene scene = sceneService.find(request.getId());
      		List<VendingContainer> list = scene.getVendingContainer();
      	    String[] propertys = {"id", "scene.name", "sn", "category.cateName", "status", "warningPer"};
      	    List<Map<String, Object>> result =
      	        FieldFilterUtils.filterCollection(propertys, list);
	    	response.setMsg(result);
	        response.setCode(CommonAttributes.SUCCESS);
	        response.setDesc(message("yxkj.request.success"));
		}else {
	        response.setCode(CommonAttributes.FAIL_COMMON);
	        response.setDesc(message("yxkj.request.failed"));
		}
      return response;
  }
  /**
   * 修改服务状态（允许服务/停止服务）
   * @param request
   * @return
   */
  @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
  @ApiOperation(value = "切换服务状态", httpMethod = "POST", response = ResponseOne.class, notes = "切换服务状态")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse vendingContainerList(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:货柜Id; status:货柜状态", required = true)
  		@RequestBody VendingContainerRequest request) {
	  BaseResponse response = new BaseResponse();
      	if (request.getId() != null && request.getContainerData() != null 
      			&& request.getContainerData().getStatus() != null) {
      		VendingContainer container = vendingContainerService.find(request.getId());
      		container.setStatus(request.getContainerData().getStatus());
      		vendingContainerService.update(container);
	        response.setCode(CommonAttributes.SUCCESS);
	        response.setDesc(message("yxkj.request.success"));
		}else {
	        response.setCode(CommonAttributes.FAIL_COMMON);
	        response.setDesc(message("yxkj.request.failed"));
		}
      return response;
  }
  /**
   * 添加货柜
   * @param request
   * @return
   */
  @RequestMapping(value = "/addContainer", method = RequestMethod.POST)
  @ApiOperation(value = "添加货柜", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse addContainer(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; containerData:货柜数据", required = true)
  		@RequestBody VendingContainerRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getContainerData() != null && request.getContainerData().getSceneId() != null) {
    	boolean exists = vendingContainerService.exists(Filter.eq("sn", request.getContainerData().getSn()));
    	if (exists) {
    	    response.setCode(CommonAttributes.FAIL_COMMON);
    	    response.setDesc(message("yxkj.vendingContainer.exists"));
    	    return response;
		}
    	vendingContainerService.saveContainer(request);
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	  }else {
	    response.setCode(CommonAttributes.FAIL_COMMON);
	    response.setDesc(message("yxkj.request.failed"));
	  }
      return response;
  }
  /**
   * 编辑货柜
   * @param request
   * @return
   */
  @RequestMapping(value = "/updateContainer", method = RequestMethod.POST)
  @ApiOperation(value = "编辑货柜", httpMethod = "POST", response = ResponseOne.class, notes = "用于编辑货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse updateContainer(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:货柜Id; containerData:货柜数据", required = true)
  		@RequestBody VendingContainerRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getId() != null && request.getContainerData() != null && request.getContainerData().getSceneId() != null) {
      	boolean exists = vendingContainerService.exists(Filter.eq("sn", request.getContainerData().getSn()), 
      			Filter.ne("id", request.getId()));
      	if (exists) {
      	    response.setCode(CommonAttributes.FAIL_COMMON);
      	    response.setDesc(message("yxkj.vendingContainer.exists"));
      	    return response;
  		}
    	vendingContainerService.updateContainer(request);
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	  }else {
	    response.setCode(CommonAttributes.FAIL_COMMON);
	    response.setDesc(message("yxkj.request.failed"));
	  }
      return response;
  }
  /**
   * 删除货柜
   * @param request
   * @return
   */
  @RequestMapping(value = "/deleteContainer", method = RequestMethod.POST)
  @ApiOperation(value = "删除货柜", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse deleteContainer(
  		@ApiParam(name = "请求参数(json)", value = "userName:用户名; ids:货柜ID数组", required = true) 
  		@RequestBody BaseRequest request) {
    BaseResponse response = new BaseResponse(); 
    if (request.getIds() != null && request.getIds().length > 0) {
    	vendingContainerService.deleteContainer(request.getIds());
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	  }else {
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(message("yxkj.request.failed"));
	  }
    return response;
  } 
  @RequestMapping(value = "/isExistSn", method = RequestMethod.POST)
  @ApiOperation(value = "查询货柜编码是否存在", httpMethod = "POST", response = ResponseOne.class, notes = "查询货柜编码是否存在")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse isExistSn(
  		@ApiParam(name = "请求参数(json)", value = "userName:用户名; sn:货柜编码; id:编辑时必填", required = true)
  		@RequestBody VendingContainerRequest request) {
      BaseResponse response = new BaseResponse(); 
      VendingContainerData containerData = request.getContainerData();
      if (containerData == null || StringUtils.isBlank(containerData.getSn())) {
          response.setCode(CommonAttributes.FAIL_LOGIN);
          response.setDesc(message("yxkj.request.param.missing"));
          return response;
		}
      boolean exist = false;
      if (containerData != null && StringUtils.isNotBlank(containerData.getSn())) {
      	if (request.getId() != null) {
				exist = vendingContainerService.exists(Filter.eq("sn", containerData.getSn()),Filter.ne("id", request.getId()));
			}else {
				exist = vendingContainerService.exists(Filter.eq("sn", containerData.getSn()));
			}
	    }
      response.setDesc(exist +"");
      response.setCode(CommonAttributes.SUCCESS);
      return response;
  }
}
