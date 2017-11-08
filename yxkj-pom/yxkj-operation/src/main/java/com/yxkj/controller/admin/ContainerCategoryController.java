package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.ContainerCategory;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.request.ContainerCategoryRequest;
import com.yxkj.json.admin.request.SearchListRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.ContainerCategoryService;
import com.yxkj.utils.FieldFilterUtils;

/**
 * 货柜类型管理 - Controller
 * @author luzhang
 */
@Controller("containerCategoryController")
@RequestMapping("/admin/containerCategory")
@Api(value = "(货柜后台)货柜类型", description = "货柜类型管理")
public class ContainerCategoryController extends BaseController {
  
  @Resource(name = "containerCategoryServiceImpl")
  private ContainerCategoryService containerCategoryService;
  
  /**
   * 货柜类型列表
   * @param request
   * @return
   */
  @RequestMapping(value = "/list", method = RequestMethod.POST)
  @ApiOperation(value = "货柜类型列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "货柜类型列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> containerCategoryList(
      @ApiParam(name = "请求参数(json)", value = "参数[userName:登录用户名]", required = false) 
      @RequestBody SearchListRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    List<ContainerCategory> list = containerCategoryService.findAll();
    String[] propertys = {"id", "cateName", "totalChannel", "capacity", "cTemp", "cntrType", "remark"};
    List<Map<String, Object>> result = FieldFilterUtils.filterCollection(propertys, list);
    response.setMsg(result);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  /**
   * 添加货柜类型
   * @param request
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ApiOperation(value = "添加货柜类型", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加货柜")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse addContainerCategory(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; containerData:货柜数据", required = true)
  		@RequestBody ContainerCategoryRequest request) {
      BaseResponse response = new BaseResponse(); 
      boolean exists = containerCategoryService.exists(Filter.eq("cTemp", request.getcTemp()), 
    		  Filter.eq("cntrType", request.getCntrType()));
      if (exists) {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.containerCategory.exists"));
          return response;
	  }
      ContainerCategory category = new ContainerCategory();
      category.setCateName(request.getCateName());
      category.setTotalChannel(request.getTotalChannel());
      category.setCapacity(request.getCapacity());
      category.setcTemp(request.getcTemp());
      category.setCntrType(request.getCntrType());
      category.setRemark(request.getRemark());      
      category.setIsActive(true);
      containerCategoryService.save(category);
      
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      return response;
  }
  /**
   * 编辑货柜类型
   * @param request
   * @return
   */
  @RequestMapping(value = "/update", method = RequestMethod.POST)
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
      boolean exists = containerCategoryService.exists(Filter.eq("cTemp", request.getcTemp()), 
    		  Filter.eq("cntrType", request.getCntrType()), Filter.ne("id", request.getId()));
      if (exists) {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.containerCategory.exists"));
          return response;
	  }
      ContainerCategory category = containerCategoryService.find(request.getId());
      category.setCateName(request.getCateName());
      category.setTotalChannel(request.getTotalChannel());
      category.setCapacity(request.getCapacity());
      category.setcTemp(request.getcTemp());
      category.setCntrType(request.getCntrType());
      category.setRemark(request.getRemark());    
      category.setIsActive(true);
      containerCategoryService.update(category);
      
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      return response;
  }
  /**
   * 删除货柜类型
   * @param request
   * @return
   */
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ApiOperation(value = "删除货柜类型", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除货柜类型")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse deleteContainerCategory(
  		@ApiParam(name = "请求参数(json)", value = "userName:用户名; ids:货柜类型ID数组", required = true) 
  		@RequestBody BaseRequest request) {
    BaseResponse response = new BaseResponse(); 
    if (request.getIds() != null && request.getIds().length > 0) {
  	  	try {
  	  		containerCategoryService.delete(request.getIds());
  	  	} catch (Exception e) {
  	  		if (e.getCause() instanceof ConstraintViolationException) {
			  response.setCode(CommonAttributes.FAIL_COMMON);
			  response.setDesc(message("yxkj.containerCategory.isUsed.cannot.delete"));
  	  		}else {
    		  response.setCode(CommonAttributes.FAIL_COMMON);              
              response.setDesc(message("yxkj.request.failed"));
  	  		}
  	  		return response;
  	  	}  
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	}else {
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(message("yxkj.request.failed"));
	}
    return response;
  } 
}
