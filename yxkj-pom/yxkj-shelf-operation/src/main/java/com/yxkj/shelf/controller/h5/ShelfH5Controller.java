package com.yxkj.shelf.controller.h5;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.entity.Company;
import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsPic;
import com.yxkj.shelf.aspect.UserValidCheck;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.json.request.UserInfoReq;
import com.yxkj.shelf.service.CompanyService;
import com.yxkj.shelf.service.GoodsService;
import com.yxkj.shelf.service.TouristService;
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

  @Resource(name = "companyServiceImpl")
  private CompanyService companyService;

  @Resource(name = "goodsServiceImpl")
  private GoodsService goodsService;

  @Resource(name = "taskExecutor")
  private TaskExecutor taskExecutor;


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
          + setting.getAuthRedirectUrl() + "?compId=" + compId + "&goodsId=" + goodsId
          + "&type=alipay";
    }
    if (hearders.contains("MicroMessenger")) {
      return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="
          + setting.getWxPublicAppId() + "&redirect_uri=" + setting.getAuthRedirectUrl()
          + "&response_type=code&scope=snsapi_userinfo&state=" + compId + "," + goodsId + ",wx";
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
  @ApiResponses({@ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  public @ResponseBody ResponseOne<Map<String, Object>> authUserInfo(
      @ApiParam(name = "请求参数(json)",
          value = "authCode:授权码|gId:商品条码号|compId:公司ID|type:支付类型wx,alipay", required = true) @RequestBody UserInfoReq req) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    Map<String, Object> resMap = new HashMap<String, Object>();
    String authCode = req.getAuthCode();
    String gId = req.getgId();
    Long compId = req.getCompId();
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
    Company company = companyService.find(compId);
    Map<String, Object> compMap = new HashMap<String, Object>();
    compMap.put("compId", compId);
    compMap.put("displayName", company.getDisplayName());
    resMap.put("compInfo", compMap);
    Goods goods = goodsService.getBySn(gId);
    Map<String, Object> gMap = new HashMap<String, Object>();
    gMap.put("gId", gId);
    gMap.put("gName", goods.getFullName());
    gMap.put("gSpec", goods.getSpec());
    gMap.put("gPrice", goods.getSalePrice());
    String gImg = "";
    for (GoodsPic goodsPic : goods.getGoodsPics()) {
      if (goodsPic.getOrder() != null && goodsPic.getOrder() == 0) {// 只获取手机显示的小图
        gImg = goodsPic.getSource();
      }
    }
    gMap.put("gImg", gImg);
    resMap.put("gInfo", gMap);

    response.setMsg(resMap);
    response.setCode(CommonAttributes.SUCCESS);

    String userId = (String) result.get("userId");
    String nickName = (String) result.get("nickname");
    response.setDesc(TokenUtil.getJWTString(userId, ""));
    taskExecutor.execute(new Runnable() {
      public void run() {
        touristService.saveTourist(userId, nickName, req.getType(), company);
      }
    });
    return response;
  }



  /**
   * 根据商品编码获取商品信息
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/getGoodsBySn", method = RequestMethod.POST)
  @ApiOperation(value = "根据商品编码获取商品信息", httpMethod = "POST", response = ResponseOne.class,
      notes = "根据商品编码获取商品信息")
  @ApiResponses({@ApiResponse(code = 200,
      message = "code:0000-request success|0004-token timeout|1000-goods not exist")})
  @UserValidCheck
  public @ResponseBody ResponseOne<Map<String, Object>> getGoodsBySn(@ApiParam(name = "请求参数(json)",
      value = "gId:商品条码号 |header token", required = true) @RequestBody UserInfoReq req) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    String gId = req.getgId();
    Goods goods = goodsService.getBySn(gId);
    if (goods == null) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.goods.noexist"));
      return response;
    }
    Map<String, Object> gMap = new HashMap<String, Object>();
    gMap.put("gId", gId);
    gMap.put("gName", goods.getFullName());
    gMap.put("gSpec", goods.getSpec());
    gMap.put("gPrice", goods.getSalePrice());
    String gImg = "";
    for (GoodsPic goodsPic : goods.getGoodsPics()) {
      if (goodsPic.getOrder() != null && goodsPic.getOrder() == 0) {// 只获取手机显示的小图
        gImg = goodsPic.getSource();
      }
    }
    gMap.put("gImg", gImg);

    response.setMsg(gMap);
    response.setCode(CommonAttributes.SUCCESS);

    return response;
  }
}
