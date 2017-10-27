package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
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
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.SceneRequest;
import com.yxkj.json.admin.request.SceneSearchRequest;
import com.yxkj.json.admin.request.SearchListRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.SceneService;
import com.yxkj.utils.FieldFilterUtils;

/**
 * 优享空间 - Controller
 * @author luzhang
 */
@Controller("sceneController")
@RequestMapping("/admin/scene")
@Api(value = "(货柜后台)优享空间", description = "优享空间")
public class SceneController extends BaseController {

  @Resource(name = "sceneServiceImpl")
  private SceneService sceneService;
  
  /**
   * 优享空间列表
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/scenePage", method = RequestMethod.POST)
  @ApiOperation(value = "优享空间列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "优享空间列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> sceneList(
      @ApiParam(name = "请求参数(json)", value = "参数[userName:登录用户名; sn:优享空间编号; name:优享空间名称]", required = false) 
      @RequestBody SceneSearchRequest request) {   
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      
    List<Filter> filters = pageable.getFilters();
    filters.add(Filter.eq("removeStatus", CommonStatus.ACITVE));
    String sn = request.getSn();
    String name = request.getName();
    if (StringUtils.isNotBlank(sn)) {
        filters.add(Filter.like("sn", "%"+sn.trim()+"%"));
    }
    if (StringUtils.isNotBlank(name)) {
        filters.add(Filter.like("name", "%"+name.trim()+"%"));
    }	
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("sn"));

    Page<Scene> scenePage = sceneService.findPage(pageable);      
    String[] propertys = {"id", "sn", "name", "openTime", "cntrTotalCount", "hasStore"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, scenePage.getContent());
    PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
  		  pageable.getPageSize(), (int)scenePage.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  
  /**
   * 获取优享空间下拉列表
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/dropDownList", method = RequestMethod.POST)
  @ApiOperation(value = "优享空间下拉列表", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "优享空间下拉列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> list(
      @ApiParam(name = "请求参数(json)", value = "参数[userName:登录用户名; key:优享空间地址或编号]", required = false) 
      @RequestBody SearchListRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    String key = request.getKey();
    List<Scene> scenes = new ArrayList<Scene>();
    List<Filter> filters = new ArrayList<Filter>();
    filters.add(Filter.eq("removeStatus", CommonStatus.ACITVE));
    if (key == null) {
      scenes = sceneService.findList(null, filters, null);
    } else {
      scenes = sceneService.getByKey(key);
    }
    String[] propertys = {"id", "name"};
    List<Map<String, Object>> result = FieldFilterUtils.filterCollection(propertys, scenes);
    response.setMsg(result);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  
  /**
   * 获取某个优享空间详情
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/getSceneData", method = RequestMethod.POST)
  @ApiOperation(value = "获取某个优享空间详情", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取某个优享空间详情")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getSceneData(
      @ApiParam(name = "请求参数(json)", value = "参数[userName:登录用户名; id:优享空间Id]", required = true) 
      @RequestBody SearchListRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    if (request.getId() == null) {
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(message("yxkj.request.failed"));
        return response;
	}
    Map<String, Object> result = sceneService.getSceneData(request.getId());
    response.setMsg(result);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  /**
   * 添加场景
   * @param request
   * @return
   */
  @RequestMapping(value = "/addScene", method = RequestMethod.POST)
  @ApiOperation(value = "添加场景", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加场景")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse addScene(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; sceneData:场景数据", required = true)
  		@RequestBody SceneRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getSceneData() != null) {
    	  //新增场景，场景的中控，中控广告
    	  sceneService.saveScene(request);
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
	  }
      return response;
  }
  /**
   * 更新场景
   * @param request
   * @return
   */
  @RequestMapping(value = "/updateScene", method = RequestMethod.POST)
  @ApiOperation(value = "更新场景", httpMethod = "POST", response = ResponseOne.class, notes = "用于更新场景")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse updateScene(
  		@ApiParam(name = "请求参数(json)", value = "userName:用户名; sceneData:场景数据", required = true)
  		@RequestBody SceneRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getSceneData() != null && request.getId() != null) {
  	  	//更新场景，场景的中控以及所有货柜的服务状态
    	sceneService.updateScene(request);
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.request.success"));
	  }
      return response;
  }
  /**
   * 删除场景（逻辑删，达到列表页面不显示）
   * @param request
   * @return
   */
  @RequestMapping(value = "/deleteScene", method = RequestMethod.POST)
  @ApiOperation(value = "删除场景", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除场景")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse deleteScene(
  		@ApiParam(name = "请求参数(json)", value = "userName:用户名; ids:场景ID数组", required = true) 
  		@RequestBody BaseRequest request) {
    BaseResponse response = new BaseResponse(); 
    if (request.getIds() != null && request.getIds().length > 0) {
    	for (Long id : request.getIds()) {
    		Scene scene = sceneService.find(id);
    		scene.setRemoveStatus(CommonStatus.INACTIVE);
    		sceneService.update(scene);
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
