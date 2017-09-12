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

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.ResponseMultiple;

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
  public @ResponseBody ResponseMultiple<Map<String, Object>> rsa(@ApiParam(name = "基础对象",
      value = "传入json格式", required = true) @RequestBody BaseRequest req) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    String key = setting.getServerPublicKey();
    response.setCode(CommonAttributes.SUCCESS);
    response.setToken(req.getUserId().toString());
    response.setDesc(key);
    return response;
  }

}
