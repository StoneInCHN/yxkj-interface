package com.yxkj.shelf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.entity.Admin;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.json.base.BaseRequest;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.utils.TokenUtil;


/**
 * Controller - 公共
 * 
 */
@Controller("commonController")
@RequestMapping("/common")
@Api(value = "(货架后台)公共", description = "Controller - 公共")
public class CommonController extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", httpMethod = "POST", response = BaseResponse.class, notes = "用户登录")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<Map<String, Object>> login(@ApiParam @RequestBody BaseRequest req) {
    	ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    	
    	//测试数据 start
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setName("superamdin");
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1L);
        map.put("userName", "admin");
        //测试数据 end
        
        response.setMsg(map);
        
        response.setCode(CommonAttributes.SUCCESS);       
        response.setToken(TokenUtil.getJWTString(req.getUserName().toString(), ""));        
        return response;
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "用户注销", httpMethod = "POST", response = BaseResponse.class, notes = "用户注销")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse logout(@ApiParam @RequestBody BaseRequest req) {
      BaseResponse response = new BaseResponse();
//      Long userId = req.getUserId();
//      String token = req.getToken();
      //删除token
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    
}
