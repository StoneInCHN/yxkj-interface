package com.yxkj.shelf.utils;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.beans.Message;
import com.yxkj.shelf.beans.Setting;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.utils.wxpay.WeixinUtil;



public class PayUtil {

  public static Setting setting = SettingUtils.get();

  // 微信公众号支付参数
  // 秘钥
  public static final String wechat_Key = setting.getWechatKey();
  // 微信分配的公众账号ID（企业号corpid即为此appId）
  public static final String wechat_appid = setting.getWechatAppid();
  // 商户ID
  public static final String wechat_mch_id = setting.getWechatMchId();
  // 通知地址
  public static final String wechat_notify_url = setting.getWechatNotifyUrl();
  // 微信下订单接口
  public static final String wechat_AddOrderUrl = setting.getWechatAddOrderUrl();


  // 支付宝手机网站支付参数
  // 支付宝公钥
  public static final String alipay_publicKey = setting.getAlipayPublicKey();
  // appid
  public static final String alipay_appId = setting.getAlipayAppId();
  // 应用私钥
  public static final String alipay_privateKey = setting.getAlipayPrivateKey();
  // 异步通知地址
  public static final String alipay_notify_url = setting.getAlipayNotifyUrl();
  // 同步通知地址
  public static final String alipay_return_url = setting.getAlipayReturnUrl();

  /**
   * create the order info for alipay. 创建订单信息
   */
  // public static String getOrderInfo(String subject, String order_sn, String body, String price) {
  // StringBuffer stringBuffer = new StringBuffer();
  //
  // // 签约合作者身份ID
  // stringBuffer.append("partner=" + "\"" + AlipayConfig.partner + "\"");
  //
  // // 签约卖家支付宝账号
  // stringBuffer.append("&seller_id=" + "\"" + AlipayConfig.sellerId + "\"");
  //
  // // 商户网站唯一订单号
  // stringBuffer.append("&out_trade_no=" + "\"" + order_sn + "\"");
  //
  // // 商品名称
  // stringBuffer.append("&subject=" + "\"" + subject + "\"");
  //
  // // 商品详情
  // stringBuffer.append("&body=" + "\"" + body + "\"");
  //
  // // 商品金额
  // stringBuffer.append("&total_fee=" + "\"" + price + "\"");
  //
  // // 服务器异步通知页面路径
  // stringBuffer.append("&notify_url=" + "\"" + AlipayConfig.ali_notify_url + "\"");
  //
  // // 服务接口名称， 固定值 mobile.securitypay.pay
  // stringBuffer.append("&service=\"mobile.securitypay.pay\"");
  //
  // // 支付类型， 固定值 1
  // stringBuffer.append("&payment_type=\"1\"");
  //
  // // 参数编码， 固定值 utf-8
  // stringBuffer.append("&_input_charset=\"utf-8\"");
  //
  // // 设置未付款交易的超时时间
  // // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
  // // 取值范围：1m～15d。
  // // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
  // // 该参数数值不接受小数点，如1.5h，可转换为90m。
  // // orderInfo += "&it_b_pay=\"30m\"";
  // stringBuffer.append("&it_b_pay=\"2d\"");
  //
  // // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
  // // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
  //
  // // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
  // // orderInfo += "&return_url=\"m.alipay.com\"";
  //
  // // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
  // // orderInfo += "&paymethod=\"expressGateway\"";
  //
  // return stringBuffer.toString();
  // }
  //
  /**
   * 支付宝支付接口
   *
   * @return
   * @throws Exception
   */
  public static String alipay(String order_sn, String subject, String total_fee) {
    AlipayClient alipayClient =
        new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipay_appId,
            alipay_privateKey, "json", "UTF-8", alipay_publicKey, "RSA"); // 获得初始化的AlipayClient
    // AlipayClient alipayClient =
    // new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipay_appId,
    // setting.getAlipayPartnerPrivateKey(), "json", "UTF-8",
    // setting.getAlipayPartnerPublicKey(), "RSA"); // 获得初始化的AlipayClient
    AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
    alipayRequest.setReturnUrl(alipay_return_url);
    alipayRequest.setNotifyUrl(alipay_notify_url);// 在公共参数中设置回跳和通知地址
    alipayRequest.setBizContent("{" + " \"out_trade_no\":" + order_sn + "," + " \"total_amount\":"
        + total_fee + "," + " \"subject\":" + subject + "," + " \"product_code\":\"QUICK_WAP_PAY\""
        + " }");// 填充业务参数
    String form = "";
    try {
      form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
      return form;
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return null;

  }


  /**
   * 微信支付接口
   * 
   * @param order_sn 商户订单号
   * @param body 商品介绍
   * @param ip
   * @param openId openId
   * @param total_fee 商品价格（分）
   * @return
   * @throws DocumentException
   */
  public static ResponseOne<Map<String, Object>> wechat(String order_sn, String body, String ip,
      String openId, String total_fee) {

    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    /**
     * 微信开发签名流程 //详情 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=4_3
     * 第一步：对参数按照key=value的格式，并按照参数名ASCII字典序排序如下： stringA=
     * "appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA"
     * ; 第二步：拼接API密钥： stringSignTemp="stringA&key=192006250b4c09247ec02edce69f6a2d"
     * sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7" 最终得到最终发送的数据：
     */
    try {
      // 随机字符串，不长于32位。推荐随机数生成算法
      String nonce_str = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
      String stringSignTemp =
          "appid=" + wechat_appid + "&body=" + body + "&mch_id=" + wechat_mch_id + "&nonce_str="
              + nonce_str + "&notify_url=" + wechat_notify_url + "&openid=" + openId
              + "&out_trade_no=" + order_sn + "&spbill_create_ip=" + ip + "&total_fee=" + total_fee
              + "&trade_type=JSAPI&key=" + wechat_Key;
      // System.out.println(stringSignTemp);
      String sign = WeixinUtil.MD5(stringSignTemp);
      // System.out.println(sign);
      String xml =
          "<xml>" + "<appid>" + wechat_appid + "</appid>" + "<body>" + body + "</body>"
              + "<mch_id>" + wechat_mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
              + "</nonce_str>" + "<notify_url>" + wechat_notify_url + "</notify_url>" + "<openid>"
              + openId + "</openid>" + "<out_trade_no>" + order_sn + "</out_trade_no>"
              + "<spbill_create_ip>" + ip + "</spbill_create_ip>" + "<total_fee>" + total_fee
              + "</total_fee>" + "<trade_type>JSAPI</trade_type>" + "<sign>" + sign + "</sign>"
              + "</xml>";
      // 调接口
      System.out.println(xml);
      String xmlString = WeixinUtil.httpsRequest(wechat_AddOrderUrl, "POST", xml);
      // 解析xml
      String prepay_id = null;
      Document dom = DocumentHelper.parseText(xmlString);
      Element root = dom.getRootElement();
      String return_code = root.elementText("return_code");
      if (return_code.equals("SUCCESS")) {
        String result_code = root.elementText("result_code");
        if (result_code.equals("SUCCESS")) {

          // 预支付交易会话标识
          prepay_id = root.elementText("prepay_id");
          Map<String, Object> data = new HashMap<String, Object>();
          data.put("nonceStr", UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
          data.put("appId", wechat_appid);
          data.put("package", "prepay_id=" + prepay_id);
          data.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
          data.put("signType", "MD5");
          data.put("paySign", WeixinUtil.getSign(data));
          data.put("out_trade_no", order_sn);
          response.setCode(CommonAttributes.SUCCESS);
          response.setMsg(data);
        } else {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(root.elementText("err_code_des")
              + Message.success("yxkj.order.create.fail", order_sn).getContent());
        }
      } else {
        response.setCode(CommonAttributes.FAIL_COMMON);
        response.setDesc(root.elementText("return_msg")
            + Message.success("yxkj.order.create.fail", order_sn).getContent());
      }
      return response;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }



}
