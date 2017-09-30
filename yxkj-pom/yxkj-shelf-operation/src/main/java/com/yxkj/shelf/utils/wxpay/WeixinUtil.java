package com.yxkj.shelf.utils.wxpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxkj.shelf.beans.Setting;
import com.yxkj.shelf.common.log.LogUtil;
import com.yxkj.shelf.utils.PayUtil;
import com.yxkj.shelf.utils.SettingUtils;


/**
 * 发送https请求的通用工具类
 * 
 * @author jack
 * @version 1.0
 * 
 */

public class WeixinUtil {
  public static Setting setting = SettingUtils.get();

  public static final String token_url = setting.getWechatTokenUrl();

  /**
   * 生成 MD5
   *
   * @param data 待处理数据
   * @return MD5结果
   */
  public static String MD5(String data) {
    try {
      java.security.MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] array = md.digest(data.getBytes("UTF-8"));
      StringBuilder sb = new StringBuilder();
      for (byte item : array) {
        sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
      }
      return sb.toString().toUpperCase();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;

  }

  /**
   * 发送https请求
   * 
   * @param requestUrl //提交的URL
   * @param requestMethod //提交方式
   * @param outputStr //ID
   * @return
   */
  public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
    String obj = null;
    // 创建SSLContext对象，并使用我们指定的信任管理器初始化
    TrustManager[] tm = {new MyX509TrustManager()};
    // 安全套接字的上下文
    SSLContext sslContext;
    try {
      sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new java.security.SecureRandom());
      // 从上述SSLContext对象中得到SSLSocketFactory对象
      SSLSocketFactory ssf = sslContext.getSocketFactory();
      // 建立连接
      URL url = new URL(requestUrl);
      // 打开连接
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      conn.setSSLSocketFactory(ssf);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      // 设置请求方式
      conn.setRequestMethod(requestMethod);

      if (null != outputStr) {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }
      // 从输入流中获取返回的内容
      InputStream inputStream = conn.getInputStream();
      InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
      BufferedReader bufferReader = new BufferedReader(reader);
      String str = null;
      StringBuffer buffer = new StringBuffer();
      while ((str = bufferReader.readLine()) != null) {
        buffer.append(str);
      }
      bufferReader.close();
      reader.close();
      inputStream.close();
      inputStream = null;

      conn.disconnect();
      obj = buffer.toString();
      LogUtil.debug(WeixinUtil.class, "httpsRequest", "response: %s", obj);
      // jsonObject = JSONObject.fromObject(buffer.toString());
    } catch (KeyManagementException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return obj;
  }


  /**
   * 
   * 获取接口访问凭证access_token 过期时间7200s
   * 
   * @param appid
   * @param appSecret
   * @return
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonParseException
   */
  public static String getToken(String appid, String appSecret) {
    Token token = null;
    try {
      // APPID&secret=APPSECRET
      // 将请求路径中的APPID和APPSECRET替换调
      String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appSecret);
      // 返回json对象
      String Str = httpsRequest(requestUrl, "GET", null);
      // 转JSON
      ObjectMapper mapper = new ObjectMapper();
      token = mapper.readValue(Str, Token.class);

      // JSONObject jsonObject = JSONObject.fromObject(Str.toString());
      // if (null != jsonObject) {
      // token = new Token();
      // // 从json对象中获取access_token和expires_in
      // token.setAccess_token(jsonObject.getString("access_token"));
      // token.setExpires_in(jsonObject.getString("expires_in"));
      // }
      return token.getAccess_token();
    } catch (Exception e) {
      e.printStackTrace();
      return "get accessToken error";
    }
  }


  /**
   * 获取jsapi ticket 过期时间7200s
   * 
   * @param token
   * @return
   * @throws IOException
   * @throws
   * @throws
   */
  public static String getJsapiTicket(String accessToken) {
    try {
      String requestUrl =
          "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken
              + "&type=jsapi";
      String Str = httpsRequest(requestUrl, "GET", null);
      // 转JSON
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> res = mapper.readValue(Str, Map.class);
      String jsTicket = (String) res.get("ticket");
      return jsTicket;
    } catch (Exception e) {
      e.printStackTrace();
      return "get jsapi ticket error";
    }
  }

  /**
   * jsapi参数签名
   * 
   * @param jsapi_ticket
   * @param url
   * @return
   */
  public static Map<String, String> jsApiSign(String jsapi_ticket, String url) {
    Map<String, String> ret = new HashMap<String, String>();
    String nonce_str = create_nonce_str();
    String timestamp = create_timestamp();
    String string1;
    String signature = "";

    // 注意这里参数名必须全部小写，且必须有序
    string1 =
        "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp
            + "&url=" + url;
    System.out.println(string1);

    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(string1.getBytes("UTF-8"));
      signature = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    ret.put("url", url);
    ret.put("jsapi_ticket", jsapi_ticket);
    ret.put("nonceStr", nonce_str);
    ret.put("timestamp", timestamp);
    ret.put("signature", signature);
    return ret;
  }

  private static String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

  private static String create_nonce_str() {
    return UUID.randomUUID().toString();
  }

  private static String create_timestamp() {
    return Long.toString(System.currentTimeMillis() / 1000);
  }

  /**
   * 根据内容类型来判断返回文件的扩展名
   * 
   * @param contentType 内容类型
   * @return
   */
  public static String getFileExt(String contentType) {
    String fileExt = "";

    if ("image/jpeg".equals(contentType))
      fileExt = ".jpg";
    else if ("audio/mpeg".equals(contentType))
      fileExt = ".mp3";
    else if ("audio/amr".equals(contentType))
      fileExt = ".amr";
    else if ("video/mp4".equals(contentType))
      fileExt = ".mp4";
    else if ("video/mpeg4".equals(contentType))
      fileExt = ".mp4";

    return fileExt;
  }

  /**
   * url编码
   * */
  public static String urlEncodeUTF8(String source) {
    String str = null;
    try {
      str = URLEncoder.encode(source, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return str;
  }

  public static String getSign(Map<String, Object> map) {
    ArrayList<String> list = new ArrayList<String>();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      if (!"sign".equals(entry.getKey()) && entry.getValue() != null
          && !"".equals(entry.getValue())) {
        list.add(entry.getKey() + "=" + entry.getValue() + "&");
      }
    }
    int size = list.size();
    String[] arrayToSort = list.toArray(new String[size]);
    Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      sb.append(arrayToSort[i]);
    }
    String result = sb.toString();
    result += "key=" + PayUtil.wechat_Key;
    result = WeixinUtil.MD5(result);
    return result;
  }

}
