package com.yxkj.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yxkj.controller.base.BaseController;

/**
 *
 * 类说明
 * 
 * @author Andrea
 * @version 创建时间：2017年10月10日 下午4:30:24
 * 
 */
@Controller("shelfH5Controller")
@RequestMapping("/h5/shelf")
public class ShelfH5Controller extends BaseController {

  @RequestMapping(value = "/{compId}/{goodsId}", method = RequestMethod.GET)
  public String scanQr(@PathVariable("compId") String compId,
      @PathVariable("goodsId") String goodsId, HttpServletRequest req) {
    String hearders = req.getHeader("user-agent");
    if (hearders.contains("AlipayClient")) {
      return "redirect:https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id="
          + setting.getAlipayAppId() + "&scope=auth_user&redirect_uri="
          + setting.getAuthRedirectUrl() + "?compId=" + compId + "&goodsId=" + goodsId
          + "&sys=shelf";
    }
    if (hearders.contains("MicroMessenger")) {
      return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="
          + setting.getWxPublicAppId() + "&redirect_uri=" + setting.getAuthRedirectUrl()
          + "&response_type=code&scope=snsapi_userinfo&state=shelf," + compId + "," + goodsId;
    }
    return "redirect:/warn.jsp";
  }


}
