package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.ContainerCategory;
import com.yxkj.entity.Scene;
import com.yxkj.entity.VendingContainer;
import com.yxkj.json.admin.request.ContainerCategoryRequest;
import com.yxkj.json.admin.request.SearchListRequest;
import com.yxkj.json.admin.request.VendingContainerRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.ContainerCategoryService;
import com.yxkj.service.ContainerChannelService;
import com.yxkj.service.SceneService;
import com.yxkj.service.VendingContainerService;
import com.yxkj.utils.FieldFilterUtils;

/**
 * 货道管理 - Controller
 * @author luzhang
 */
@Controller("containerChannelController")
@RequestMapping("/admin/containerChannel")
@Api(value = "(货柜后台)货道管理", description = "货道管理")
public class ContainerChannelController extends BaseController {

  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;
  
  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;
  
  @Resource(name = "containerCategoryServiceImpl")
  private ContainerCategoryService containerCategoryService;
  
 
  @RequestMapping(value = "/containerChannelList", method = RequestMethod.POST)
  @ApiOperation(value = "单个货柜的货道列表", httpMethod = "POST", response = ResponseOne.class, notes = "单个货柜的货道列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> vendingContainerList(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:货柜Id", required = true)
  		@RequestBody BaseRequest request) {
	  	ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
      	if (request.getId() != null) {
      		VendingContainer container = vendingContainerService.find(request.getId());
      		container.getCntrChannel()
      		List<VendingContainer> list = scene.getVendingContainer();
      	    String[] propertys = {"id", "scene.name", "sn", "category.cateName", "status"};
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
  
  @RequestMapping(value = "/addContainer", method = RequestMethod.POST)
  @ApiOperation(value = "添加货柜", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse addContainer(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; containerData:货柜数据", required = true)
  		@RequestBody VendingContainerRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getContainerData() != null && request.getContainerData().getSceneId() != null) {
    	vendingContainerService.saveContainer(request);
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	  }else {
	        response.setCode(CommonAttributes.FAIL_COMMON);
	        response.setDesc(message("yxkj.request.failed"));
	  }
      return response;
  }
  
  @RequestMapping(value = "/updateContainer", method = RequestMethod.POST)
  @ApiOperation(value = "编辑货柜", httpMethod = "POST", response = ResponseOne.class, notes = "用于编辑货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse updateContainer(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:货柜Id; containerData:货柜数据", required = true)
  		@RequestBody VendingContainerRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getId() != null && request.getContainerData() != null 
    		  && request.getContainerData().getSceneId() != null) {
    	vendingContainerService.updateContainer(request);
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	  }else {
	        response.setCode(CommonAttributes.FAIL_COMMON);
	        response.setDesc(message("yxkj.request.failed"));
	  }
      return response;
  }
  
  @RequestMapping(value = "/deleteContainer", method = RequestMethod.POST)
  @ApiOperation(value = "删除货柜", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse deleteContainer(
  		@ApiParam(name = "请求参数(json)", value = "userName:用户名; ids:货柜ID数组", required = true) 
  		@RequestBody BaseRequest request) {
    BaseResponse response = new BaseResponse(); 
    if (request.getIds() != null && request.getIds().length > 0) {
    	vendingContainerService.delete(request.getIds());
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	  }else {
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(message("yxkj.request.failed"));
	  }
    return response;
  } 
  
  
  @RequestMapping(value = "/containerCategoryList", method = RequestMethod.POST)
  @ApiOperation(value = "货柜类型列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "货柜类型列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> containerCategoryList(
      @ApiParam(name = "请求参数(json)", value = "参数[userName:登录用户名; key:优享空间地址或编号]", required = false) 
      @RequestBody SearchListRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    List<ContainerCategory> list = containerCategoryService.findAll();
    String[] propertys = {"id", "cateName" , "capacity", "cTemp", "cntrType", "remark"};
    List<Map<String, Object>> result = FieldFilterUtils.filterCollection(propertys, list);
    response.setMsg(result);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  @RequestMapping(value = "/addContainerCategory", method = RequestMethod.POST)
  @ApiOperation(value = "添加货柜类型", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse addContainerCategory(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; containerData:货柜数据", required = true)
  		@RequestBody ContainerCategoryRequest request) {
      BaseResponse response = new BaseResponse(); 
      ContainerCategory category = new ContainerCategory();
      category.setCapacity(request.getCapacity());
      category.setCateName(request.getCateName());
      category.setRemark(request.getRemark());
      category.setcTemp(request.getcTemp());
      containerCategoryService.save(category);
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      return response;
  }
  
  @RequestMapping(value = "/updateContainerCategory", method = RequestMethod.POST)
  @ApiOperation(value = "编辑货柜类型", httpMethod = "POST", response = ResponseOne.class, notes = "用于编辑货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse updateContainerCategory(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:货柜类型Id; containerData:货柜数据", required = true)
  		@RequestBody ContainerCategoryRequest request) {
      BaseResponse response = new BaseResponse(); 
      if(request.getId() == null){
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.failed"));
          return response;
      }
      ContainerCategory category = containerCategoryService.find(request.getId());
      category.setCapacity(request.getCapacity());
      category.setCateName(request.getCateName());
      category.setRemark(request.getRemark());
      category.setcTemp(request.getcTemp());
      containerCategoryService.update(category);
      
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      return response;
  }
  @RequestMapping(value = "/deleteContainerCategory", method = RequestMethod.POST)
  @ApiOperation(value = "删除货柜类型", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除货柜类型")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse deleteContainerCategory(
  		@ApiParam(name = "请求参数(json)", value = "userName:用户名; ids:货柜类型ID数组", required = true) 
  		@RequestBody BaseRequest request) {
    BaseResponse response = new BaseResponse(); 
    if (request.getIds() != null && request.getIds().length > 0) {
    	containerCategoryService.delete(request.getIds());
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	  }else {
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(message("yxkj.request.failed"));
	  }
    return response;
  } 
}
