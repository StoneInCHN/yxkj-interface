package com.yxkj.shelf.controller.h5;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.json.request.UserInfoReq;
import com.yxkj.shelf.service.TouristService;
import com.yxkj.shelf.utils.ApiUtils;
import com.yxkj.shelf.utils.TokenUtil;

/**
 * Controller - 货架H5
 * 
 */
@Controller("shelfH5Controller")
@RequestMapping("/h5")
@Api(value = "货架H5页面", description = "货架H5页面")
public class ShelfH5Controller extends BaseController {

  @Resource(name = "touristServiceImpl")
  private TouristService touristService;


  /**
   * 扫码货架二维码信息
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/shelf/{compId}/{goodsId}", method = RequestMethod.GET)
  @ApiOperation(value = "扫码二维码授权", httpMethod = "GET", notes = "扫码二维码授权")
  public String scanQr(@PathVariable("compId") String compId,
      @PathVariable("goodsId") String goodsId, HttpServletRequest req, HttpServletResponse res) {
    String hearders = req.getHeader("user-agent");
    if (hearders.contains("AlipayClient")) {
      return "redirect:https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id="
          + setting.getAlipayAppId() + "&scope=auth_user&redirect_uri="
          + setting.getAuthRedirectUrl() + "?compId=" + compId + "&goodsId=" + goodsId;
    }
    if (hearders.contains("MicroMessenger")) {
      return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="
          + setting.getWxPublicAppId() + "&redirect_uri=" + setting.getAuthRedirectUrl()
          + "&response_type=code&scope=snsapi_userinfo&state=" + compId + "," + goodsId;
    }
    return null;
  }

  /**
   * 获取授权用户信息(包括支付宝,微信用户)
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/authUserInfo", method = RequestMethod.POST)
  @ApiOperation(value = "获取授权用户信息", httpMethod = "POST", response = ResponseOne.class,
      notes = "获取授权用户信息(微信及支付宝)")
  public @ResponseBody ResponseOne<Map<String, Object>> login(@ApiParam(name = "用户对象",
      value = "传入json格式", required = true) @RequestBody UserInfoReq req) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    String authCode = req.getAuthCode();
    if ("wx".equals(req.getType())) {
      String url =
          "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + setting.getWxPublicAppId()
              + "&secret=" + setting.getWxPublicAppSecret() + "&code=" + authCode
              + "&grant_type=authorization_code";
      String auth_token = ApiUtils.get(url);
    }
    if ("alipay".equals(req.getType())) {

    }
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(TokenUtil.getJWTString("", ""));
    return response;
  }
}
