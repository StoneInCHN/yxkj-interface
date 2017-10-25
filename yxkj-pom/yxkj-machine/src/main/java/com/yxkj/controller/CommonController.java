package com.yxkj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.aspect.UserValidCheck;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.request.UserInfoReq;
import com.yxkj.service.CmdService;
import com.yxkj.service.GoodsService;
import com.yxkj.service.OrderService;
import com.yxkj.service.TouristService;
import com.yxkj.utils.TokenUtil;

/**
 * Controller - 共用
 */
@Controller("commonController")
@RequestMapping("/common")
@Api(value = "common", description = "公共方法")
// 添加注释
public class CommonController extends MobileBaseController {

  @Resource(name = "cmdServiceImpl")
  private CmdService cmdService;

  @Resource(name = "touristServiceImpl")
  private TouristService touristService;

  @Resource(name = "goodsServiceImpl")
  private GoodsService goodsService;

  @Resource(name = "orderServiceImpl")
  private OrderService orderService;

  // @Resource(name = "taskExecutor")
  // private TaskExecutor taskExecutor;


  /**
   * 获取授权用户信息(包括支付宝,微信用户)
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/authUserInfo", method = RequestMethod.POST)
  @ApiOperation(value = "获取授权用户信息", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取授权用户信息(微信及支付宝)")
  @ApiResponses({@ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  public @ResponseBody ResponseOne<Map<String, Object>> authUserInfo(
      @ApiParam(name = "请求参数(json)", value = "authCode:授权码|gList:商品集合字符串|type:支付类型wx,alipay",
          required = true) @RequestBody UserInfoReq req) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    Map<String, Object> resMap = new HashMap<String, Object>();
    String authCode = req.getAuthCode();
    Map<String, Object> result = null;
    if ("wx".equals(req.getType())) {
      result =
          touristService.getWxUserInfo(setting.getWxPublicAppId(), setting.getWxPublicAppSecret(),
              authCode);
    }
    if ("alipay".equals(req.getType())) {
      result =
          touristService.getAlipayUserInfo(setting.getAlipayPublicKey(),
              setting.getAlipayPrivateKey(), setting.getAlipayAppId(), authCode);
    }
    if (result == null || result.isEmpty()) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.auth.failed"));
      return response;
    }
    resMap.put("userInfo", result);

    List<Map<String, Object>> gMap = goodsService.getGforScanH5(req.getgList());
    resMap.put("gInfo", gMap);

    response.setMsg(resMap);
    response.setCode(CommonAttributes.SUCCESS);

    String userId = (String) result.get("userId");
    String nickName = (String) result.get("nickname");
    response.setToken(TokenUtil.getJWTString(userId, ""));
    touristService.saveTourist(userId, nickName, req.getType(), req.getImei());
    // taskExecutor.execute(new Runnable() {
    // public void run() {
    // touristService.saveTourist(userId, nickName, req.getType(), company);
    // }
    // });
    return response;
  }


  /**
   * 获取微信jsapi参数信息
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/jsapiConfig", method = RequestMethod.POST)
  @ApiOperation(value = "获取微信jsapi参数信息", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取微信jsapi参数信息")
  @ApiResponses({@ApiResponse(code = 200, message = "code:0000-request success|0004-token timeout")})
  @UserValidCheck
  public @ResponseBody ResponseOne<Map<String, Object>> jsapiConfig(
      @ApiParam(name = "请求参数(json)", value = "curUrl:当前网页url地址  |userName:用户标识|header token",
          required = true) @RequestBody UserInfoReq req) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();

    Map<String, Object> res =
        touristService.getJsapiConfig(req.getCurUrl(), setting.getWxPublicAppId(),
            setting.getWxPublicAppSecret());
    res.put("appId", setting.getWxPublicAppId());
    response.setMsg(res);
    response.setCode(CommonAttributes.SUCCESS);

    return response;
  }
}
