package com.yxkj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.json.base.BaseResponse;

/**
 * Controller - 共用
 * 
 */
@Controller("commonController")
@RequestMapping("/common")
@Api(description = "注册版块", value = "common")
// 添加注释
public class CommonController extends MobileBaseController {

  /**
   * 获取公匙
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/rsa", method = RequestMethod.POST)
  @ApiOperation(value = "rsa", httpMethod = "POST", notes = "根据用户名获取用户对象")
  public @ResponseBody BaseResponse rsa() {
    BaseResponse response = new BaseResponse();
    String key = setting.getServerPublicKey();
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(key);
    return response;
  }

}
