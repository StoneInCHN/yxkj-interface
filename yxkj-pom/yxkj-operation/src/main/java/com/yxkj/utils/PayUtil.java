package com.yxkj.utils;


import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.yxkj.beans.Setting;
import com.yxkj.common.log.LogUtil;
import com.yxkj.utils.wxpay.WeixinUtil;



public class PayUtil {

  public static Setting setting = SettingUtils.get();

  // 微信公众号支付参数
  // 秘钥
  public static final String wechat_Key = setting.getWechatKey();
  // 微信分配的公众账号ID（企业号corpid即为此appId）
  public static final String wechat_appid = setting.getWechatAppid();
  // 商户ID
  public static final String wechat_mch_id = setting.getWechatMchId();
  // 微信退款接口
  public static final String wechat_RefundUrl = setting.getWechatRefundUrl();

  // 支付宝手机网站支付参数
  // 支付宝公钥
  public static final String alipay_publicKey = setting.getAlipayPublicKey();
  // appid
  public static final String alipay_appId = setting.getAlipayAppId();
  // 应用私钥
  public static final String alipay_privateKey = setting.getAlipayPrivateKey();


  /**
   * 支付宝退款接口
   *
   * @return
   * @throws Exception
   */
  public static Boolean aliRefund(String order_sn, String refundAmount) {
    AlipayTradeRefundModel model = new AlipayTradeRefundModel();
    model.setOutRequestNo(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    model.setRefundAmount(refundAmount);
    model.setOutTradeNo(order_sn);
    AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
    request.setBizModel(model);

    AlipayClient alipayClient =
        new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", alipay_appId,
            alipay_privateKey, "json", "UTF-8", alipay_publicKey, "RSA");
    Boolean resposne = false;
    try {
      AlipayTradeRefundResponse alipayTradeRefundResponse = alipayClient.execute(request);
      if ("10000".equals(alipayTradeRefundResponse.getCode())) {
        // success
        resposne = true;
      } else {
        resposne = false;
        LogUtil.error(PayUtil.class, "aliRefund",
            "aliRefund failed:" + alipayTradeRefundResponse.getMsg());
      }
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return resposne;
  }


  /**
   * 微信退款接口
   * 
   * @param order_sn 商户订单号
   * @param total_fee 订单总金额
   * @param refund_fee 退款金额
   * @param refundSn
   * @return 退款成功或者失败
   */
  public static Boolean weRefund(String order_sn, String total_fee, String refund_fee,
      String refundSn) {
    LogUtil.debug(PayUtil.class, "weRefund", "orderSn:%s,total_fee:%s,refund_fee:%s,refundSn:%s",
        order_sn, total_fee, refund_fee, refundSn);
    // 随机字符串，不长于32位。推荐随机数生成算法
    String nonce_str = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    String stringSignTemp =
        "appid=" + wechat_appid + "&mch_id=" + wechat_mch_id + "&nonce_str=" + nonce_str
            + "&out_refund_no=" + refundSn + "&out_trade_no=" + order_sn + "&refund_fee="
            + refund_fee + "&total_fee=" + total_fee + "&key=" + wechat_Key;
    // System.out.println(stringSignTemp);
    String sign = WeixinUtil.MD5(stringSignTemp);
    // System.out.println(sign);
    String xml =
        "<xml>" + "<appid>" + wechat_appid + "</appid>" + "<mch_id>" + wechat_mch_id + "</mch_id>"
            + "<nonce_str>" + nonce_str + "</nonce_str>" + "<out_trade_no>" + order_sn
            + "</out_trade_no>" + "<total_fee>" + total_fee + "</total_fee>" + "<refund_fee>"
            + refund_fee + "</refund_fee>" + "<out_refund_no>" + refundSn + "</out_refund_no>"
            + " <transaction_id></transaction_id>" + "<sign>" + sign + "</sign>" + "</xml>";
    // 调接口
    System.out.println(xml);

    // 解析xml
    boolean response = false;
    try {
      String xmlString =
          WeixinUtil.httpsRequestWithCert(wechat_RefundUrl, xml, wechat_mch_id,
              setting.getPkcs12Path());
      Document dom = DocumentHelper.parseText(xmlString);
      Element root = dom.getRootElement();
      String return_code = root.elementText("return_code");
      if (return_code.equals("SUCCESS")) {
        response = true;
      } else {
        response = false;
        LogUtil.debug(PayUtil.class, "weRefund", "Refund Failed:" + root.elementText("return_msg"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return response;
  }
}
