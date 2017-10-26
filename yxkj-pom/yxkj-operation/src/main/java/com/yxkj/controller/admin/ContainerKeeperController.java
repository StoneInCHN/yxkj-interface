package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.ContainerKeeper;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.PropertyKeeperRequest;
import com.yxkj.json.base.BaseListRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.ContainerKeeperService;
import com.yxkj.utils.ExportHelper;
import com.yxkj.utils.FieldFilterUtils;
import com.yxkj.utils.GenerateRandom;
import com.yxkj.utils.ToolsUtils;

/**
 * Controller - 管家管理
 * @author luzhang
 *
 */
@Controller("containerKeeperController")
@RequestMapping("/admin/containerKeeper")
@Api(value = "(货柜后台)管家管理页面", description = "管家管理页面")
public class ContainerKeeperController extends BaseController {   
	
	@Resource(name = "containerKeeperServiceImpl")
	private ContainerKeeperService containerKeeperService;
	
	@Autowired
	private ExportHelper exportHelper;	
	
    @RequestMapping(value = "/getKeeperList", method = RequestMethod.POST)
    @ApiOperation(value = "管家列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于获取管家列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Map<String, Object>> getKeeperList(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; pageNumber:页码; pageSize:每页数量", required = false) 
    		@RequestBody BaseListRequest request) {
    	
      ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
      Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      

      Page<ContainerKeeper> keeperPage = containerKeeperService.findPage(pageable);      
      String[] propertys = {"id", "userName", "cellPhoneNum", "scenes"};
      List<Map<String, Object>> result = FieldFilterUtils.filterCollection(propertys, keeperPage.getContent());
      PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
    		  pageable.getPageSize(), (int)keeperPage.getTotal());
      response.setPage(pageInfo);
      response.setMsg(result);

      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    
    @RequestMapping(value = "/addKeeper", method = RequestMethod.POST)
    @ApiOperation(value = "添加管家", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加管家")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse addGoods(
    		@ApiParam (name = "请求参数(json)", value = "userName:用户名; realName:管家姓名; cellPhoneNum:手机号; sceneIds:负责优享空间IDs", required = true)
    		@RequestBody PropertyKeeperRequest request) {
        BaseResponse response = new BaseResponse(); 
        if (request.getSceneIds() != null && request.getSceneIds().length > 0 ) {      
      	    ContainerKeeper keeper = containerKeeperService.getContainerKeeperEntity(request, null);
      	    String loginPwd = keeper.getLoginPwd();
      	    keeper.setLoginPwd(DigestUtils.md5Hex(loginPwd));      	    
      	    containerKeeperService.saveKeeper(keeper);      	   
		    ToolsUtils.sendSmsMsg(request.getCellPhoneNum(), message("yxkj.containerKeeper.create.sendSMS", 
		    		keeper.getRealName(), keeper.getUserName(), loginPwd));
            response.setCode(CommonAttributes.SUCCESS);
            response.setDesc(message("yxkj.request.success"));
  	  	}else {
            response.setCode(CommonAttributes.FAIL_COMMON);
            response.setDesc(message("yxkj.request.failed"));
		}
        return response;
    }
    @RequestMapping(value = "/updateKeeper", method = RequestMethod.POST)
    @ApiOperation(value = "更新管家", httpMethod = "POST", response = ResponseOne.class, notes = "用于更新管家")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse updateGoods(
    		@ApiParam(name = "请求参数(json)", value = "serName:用户名; realName:管家姓名; cellPhoneNum:手机号; sceneIds:负责优享空间IDs", required = true)
    		@RequestBody PropertyKeeperRequest request) {
        BaseResponse response = new BaseResponse(); 
        if (request.getId() != null && request.getSceneIds() != null && request.getSceneIds().length > 0 ) {      
      	    containerKeeperService.updateKeeper(request);
            response.setCode(CommonAttributes.SUCCESS);
            response.setDesc(message("yxkj.request.success"));
  	  	}else {
            response.setCode(CommonAttributes.FAIL_COMMON);
            response.setDesc(message("yxkj.request.failed"));
		}
        return response;
    }
    
    @RequestMapping(value = "/deleteKeeper", method = RequestMethod.POST)
    @ApiOperation(value = "删除管家", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除管家")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse deleteGoods(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; ids:管家ID数组", required = true) 
    		@RequestBody BaseRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getIds() != null && request.getIds().length > 0) {
    	  containerKeeperService.deleteKeeper(request.getIds());
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
	  }else {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.failed"));
	  }
      return response;
    } 
    
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ApiOperation(value = "管家重置（随机）密码", httpMethod = "POST", response = ResponseOne.class, notes = "管家重置（随机）密码")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse resetPwd(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; id:管家ID", required = true) 
    		@RequestBody BaseRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getId() == null) {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.failed"));   	  
      }
      ContainerKeeper keeper = containerKeeperService.find(request.getId());
      String newPwd = new GenerateRandom().createPassWord(10);//生成随机密码
      keeper.setLoginPwd(DigestUtils.md5Hex(newPwd));
      containerKeeperService.update(keeper);
	  ToolsUtils.sendSmsMsg(keeper.getCellPhoneNum(), message("yxkj.containerKeeper.resetPwd.sendSMS", 
	    		keeper.getRealName(), keeper.getUserName(), newPwd));
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      
      return response;
    } 
 
}
