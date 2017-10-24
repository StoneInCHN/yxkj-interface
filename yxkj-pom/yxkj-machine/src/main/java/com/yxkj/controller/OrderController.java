package com.yxkj.controller;

import com.yxkj.beans.Message;
import com.yxkj.entity.commonenum.CommonEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.AlipaySignature;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Order;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.beans.GoodsBean;
import com.yxkj.json.request.OrderInfoReq;
import com.yxkj.service.ContainerChannelService;
import com.yxkj.service.GoodsCategoryService;
import com.yxkj.service.GoodsService;
import com.yxkj.service.OrderService;
import com.yxkj.utils.PayUtil;
import com.yxkj.utils.alipay.AuthUtil;
import com.yxkj.utils.wxpay.WeixinUtil;

/**
 * Controller - 订单
 */
@Controller("OrderController")
@RequestMapping("/order")
@Api(value = "订单", description = "订单")
public class OrderController extends MobileBaseController {

  @Resource(name = "goodsServiceImpl")
  private GoodsService goodsService;

  @Resource(name = "goodsCategoryServiceImpl")
  private GoodsCategoryService goodsCategoryService;

  @Resource(name = "orderServiceImpl")
  private OrderService orderService;


  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;

  @Resource(name = "taskExecutor")
  private TaskExecutor taskExecutor;

  /**
   * 商品支付
   * 
   * @param
   * @return
   * @throws UnsupportedEncodingException
   */
  @RequestMapping(value = "/pay", method = RequestMethod.POST)
  @ApiOperation(value = "商品支付", httpMethod = "POST", response = ResponseOne.class, notes = "商品支付")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|0004-token timeout")})
  // @UserValidCheck
  public @ResponseBody ResponseOne<Map<String, Object>> pay(@ApiParam(name = "请求参数(json)",
      value = "ip:客户端ip|gInfo:下单商品信息 |userName:用户标识|imei:中控标识imei|type:支付方式|header token",
      required = true) @RequestBody OrderInfoReq req, HttpServletResponse httpResponse) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    Map<String, Object> result = new HashMap<String, Object>();
    String userName = req.getUserName();
    String type = req.getType();
    String ip = req.getIp();
    String imei = req.getImei();
    List<String> gInfo = req.getgInfo();
    LogUtil.debug(this.getClass(), "pay",
        "request param: userName:%s,imei: %s,payType: %s, gInfo: %s,ip: %s", userName, imei, type,
        gInfo.toString(), ip);
    if (CollectionUtils.isEmpty(gInfo)) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.param.missing"));
      return response;
    }
    List<GoodsBean> goodsBeans = new ArrayList<GoodsBean>();
    for (String info : gInfo) {
      String[] gf = info.split("-");
      String cId = gf[0];
      Integer count = Integer.parseInt(gf[1]);
      ContainerChannel cc = containerChannelService.find(Long.valueOf(cId));
      // if (count > cc.getSurplus()) {
      // response.setCode(CommonAttributes.FAIL_COMMON);
      // response.setDesc(message("yxkj.goods.stock.insufficient"));
      // return response;
      // }
      GoodsBean bean = new GoodsBean();
      bean.setChannel(cc);
      bean.setCount(count);
      goodsBeans.add(bean);
    }
    boolean verifyResult = containerChannelService.isVerifyStockSuccess(goodsBeans,
        CommonEnum.PurMethod.CONTROLL_MACHINE);
    if (!verifyResult) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(Message.warn("yxkj.goods.stock.insufficient").getContent());
      result.put("verifyResult", verifyResult);
      response.setMsg(result);
    }
    Order order = orderService.createCntrOrder(type, userName, imei, goodsBeans);
    LogUtil.debug(this.getClass(), "pay", "create cntr order: orderSn:%s,payType: %s, imei: %s",
        order.getSn(), type, imei);
    if ("wx".equals(type)) {
      BigDecimal weChatPrice = order.getAmount().multiply(new BigDecimal(100));
      response = PayUtil.wechat(order.getSn(), setting.getSiteName(), ip, userName,
          weChatPrice.intValue() + "");
    } else if ("alipay".equals(type)) {
      BigDecimal alipayPrice = order.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
      String form = PayUtil.alipay(order.getSn(),
          AuthUtil.URLEncode(setting.getSiteName(), "UTF-8"), alipayPrice.toString());
      result.put("a_page", form);
      response.setMsg(result);

      // try {
      // httpResponse.setContentType("text/html;charset=UTF-8");
      // httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
      // httpResponse.getWriter().flush();
      // httpResponse.getWriter().close();
      // } catch (Exception e) {
      // e.printStackTrace();
      // }


    }
    // response.setMsg(gMap);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


  /**
   * 微信支付回调接口
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/pay/notify_wechat", method = RequestMethod.POST)
  public @ResponseBody String notify_wechat(HttpServletRequest request) throws Exception {
    // 获取支付通知xml数据
    ServletInputStream inputStream = request.getInputStream();
    InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
    BufferedReader bufferReader = new BufferedReader(reader);
    String xml = null;
    StringBuffer buffer = new StringBuffer();
    while ((xml = bufferReader.readLine()) != null) {
      buffer.append(xml);
    }
    bufferReader.close();
    reader.close();
    inputStream.close();
    inputStream = null;
    // 解析xml数据
    Map<String, Object> xmlMap = new HashMap<String, Object>();
    // 解析XML数据
    Document doc = DocumentHelper.parseText(buffer.toString());
    Element root = doc.getRootElement();
    Iterator it = root.elementIterator();
    while (it.hasNext()) {
      Element temp = (Element) it.next();
      xmlMap.put(temp.getName(), temp.getStringValue());
    }
    LogUtil.debug(this.getClass(), "notify_wechat",
        "wechat pay notify callback method. response: %s", xmlMap);
    String xmlReturn = "";
    if ("SUCCESS".equals(xmlMap.get("return_code"))) {
      // 验证签名
      String sign = xmlMap.get("sign") + "";
      // xmlMap.put("sign", "");
      if (sign.equals(WeixinUtil.getSign(xmlMap))) {
        // 查询是否处理过
        // 处理回调结果
        if ("SUCCESS".equals(xmlMap.get("result_code"))) {
          // 系统订单编号
          String out_trade_no = xmlMap.get("out_trade_no").toString();
          // 支付的金额
          String total_fee = xmlMap.get("total_fee").toString();
          // 货币种类
          // String fee_type = xmlMap.get("fee_type").toString();
          // 微信支付订单号
          // String transaction_id = xmlMap.get("transaction_id").toString();

          BigDecimal amount = new BigDecimal(total_fee).divide(new BigDecimal(100));
          LogUtil.debug(this.getClass(), "notify_wechat",
              "user pay order call back successfully with wechat pay. orderSn: %s, amount: %s",
              out_trade_no, amount);

          taskExecutor.execute(new Runnable() {
            public void run() {
              orderService.callbackAfterPay(out_trade_no);
            }
          });

        } else {
          LogUtil.debug(this.getClass(), "notify_wechat", "WeChat pay fail. orderSn: %s",
              xmlMap.get("out_trade_no").toString());
        }
      }
      // 返回处理结果xml
      xmlReturn =
          "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    } else {
      // 返回处理结果xml
      xmlReturn = "<xml><return_code>FAIL</return_code><return_msg>签名错误</return_msg></xml>";
    }

    return xmlReturn;
  }


  /**
   * 支付宝支付异步通知
   * 
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/pay/notify_alipay", method = RequestMethod.POST)
  public @ResponseBody String notify_alipay(HttpServletRequest request) throws Exception {


    // 获取支付宝POST过来反馈信息
    Map<String, String> params = new HashMap<String, String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
      }
      // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
      // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
      params.put(name, valueStr);
    }
    LogUtil.debug(this.getClass(), "notify_alipay",
        "Alipay pay notify callback method. response: %s", params);

    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
    // 商户订单号
    String out_trade_no =
        new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
    // 支付宝交易号
    String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
    // 交易状态
    String trade_status =
        new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
    // 交易金额
    String total_fee =
        new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
    // 购买者id
    // String buyer_id = new String(request.getParameter("buyer_id").getBytes("ISO-8859-1"),
    // "UTF-8");
    // 购买中邮箱
    // String buyer_eamil =
    // new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");
    boolean signVerified =
        AlipaySignature.rsaCheckV1(params, setting.getAlipayPublicKey(), "UTF-8", "RSA"); // 调用SDK验证签名
    if (signVerified) {
      if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {

        LogUtil.debug(this.getClass(), "notify_alipay",
            "user pay order call back successfully with alipay. orderSn: %s, amount: %s, trade_status: %s",
            out_trade_no, total_fee, trade_status);
        taskExecutor.execute(new Runnable() {
          public void run() {
            orderService.callbackAfterPay(out_trade_no);
          }
        });

      }
      return "success"; // 请不要修改或删除
    } else {
      return "fail";
    }

  }
}
