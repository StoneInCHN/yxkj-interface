package com.yxkj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.aspect.UserValidCheck;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.utils.TokenUtil;

/**
 * Controller - 共用
 * 
 */
@Controller("commonController")
@RequestMapping("/common")
@Api(value = "common", description = "公共方法")
// 添加注释
public class CommonController extends MobileBaseController {

  /**
   * 获取公匙
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/rsa", method = RequestMethod.POST)
  @ApiOperation(value = "rsa", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "获取公钥")
  @UserValidCheck
  public @ResponseBody ResponseMultiple<Map<String, Object>> rsa(@ApiParam(name = "基础对象",
      value = "传入json格式", required = true) @RequestBody BaseRequest req) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    String token = req.getToken();
    // if (TokenUtil.parseJWT(token) == null) {
    // response.setCode(CommonAttributes.FAIL_COMMON);
    // response.setToken(req.getUserId().toString());
    // response.setDesc("token非法");
    // return response;
    // }
    response.setCode(CommonAttributes.SUCCESS);
    response.setToken(req.getUserId().toString());
    return response;
  }


  /**
   * 用户登录
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ApiOperation(value = "login", httpMethod = "POST", response = BaseResponse.class, notes = "用户登录")
  public @ResponseBody BaseResponse login(@ApiParam(name = "基础对象", value = "传入json格式",
      required = true) @RequestBody BaseRequest req) {
    BaseResponse response = new BaseResponse();
    String key = setting.getServerPublicKey();
    response.setCode(CommonAttributes.SUCCESS);
    response.setToken(req.getUserId().toString());
    response.setDesc(TokenUtil.getJWTString(req.getUserId().toString(), "", 1000000));
    return response;
  }
}
