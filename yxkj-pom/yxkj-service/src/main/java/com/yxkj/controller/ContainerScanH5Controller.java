package com.yxkj.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yxkj.controller.base.BaseController;

/**
 * 货柜
 * 
 * @author Andrea
 * @version 2017年10月12日 下午3:42:34
 */
@Controller("containerScanH5Controller")
@RequestMapping("/scan/h5/cntr")
public class ContainerScanH5Controller extends BaseController {

  /**
   * 货柜商品支付二维码链接
   *
   * @param imei 设备号
   * @param cntrId 货道ID
   * @param req
   * @return
   */
  @RequestMapping(value = "/{imei}/{cntrId}", method = RequestMethod.GET)
  public String scanGoodsQr(@PathVariable("imei") String imei, @PathVariable("cntrId") Long cntrId,
      HttpServletRequest req) {
    String hearders = req.getHeader("user-agent");
    if (hearders.contains("AlipayClient")) {
      return "redirect:https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id="
          + setting.getAlipayAppId() + "&scope=auth_user&redirect_uri="
          + setting.getAuthRedirectUrl() + "?imei=" + imei + "&cntrId=" + cntrId + "&sys=cntrGoods";
    }
    if (hearders.contains("MicroMessenger")) {
      return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="
          + setting.getWxPublicAppId() + "&redirect_uri=" + setting.getAuthRedirectUrl()
          + "&response_type=code&scope=snsapi_userinfo&state=cntr," + imei + "," + cntrId;
    }
    return "redirect:/warn.jsp";
  }


}
