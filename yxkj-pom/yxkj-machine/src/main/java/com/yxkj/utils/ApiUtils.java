package com.yxkj.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.yxkj.beans.Setting;
import com.yxkj.common.log.LogUtil;



/**
 * API工具类
 * 
 * @author sujinxuan
 * 
 */
public final class ApiUtils {

  public static Setting setting = SettingUtils.get();

  private static int TIME_OUT = 1000;



  /**
   * 默认按照UTF-8读取
   * 
   * @param url
   * @return
   */
  public static String post(String url) {
    return post(url, "UTF-8", "UTF-8");
  }

  /**
   * 默认按照UTF-8读取
   * 
   * @param url
   * @param params 参数：id=1&type=0
   * @return
   */
  public static String post(String url, String params) {
    return post(url, "UTF-8", "UTF-8", params);
  }

  /**
   * 默认按照UTF-8读取
   * 
   * @param url
   * @param params 参数
   * @return
   * @throws Exception
   */
  public static String post(String url, Map<String, Object> params) throws Exception {
    String query = parseMap(params, "UTF-8");
    return post(url, query);
  }

  /**
   * 解析参数map
   * 
   * @param paramMap
   * @return
   * @throws UnsupportedEncodingException
   */
  private static String parseMap(Map<String, Object> paramMap, String pEnv) throws Exception {
    String target = "";
    @SuppressWarnings("rawtypes")
    Iterator ite = paramMap.keySet().iterator();
    while (ite.hasNext()) {
      String key = ite.next().toString();
      target += key + "=" + URLEncoder.encode(paramMap.get(key).toString(), pEnv) + "&";
    }
    return target.endsWith("&") ? target.substring(0, target.length() - 1) : target;
  }

  /**
   * 按照charset格式编码读取
   * 
   * @param url
   * @param p_charset 发送编码
   * @param r_charset 接收编码
   * @return
   */
  public static String post(String url, String p_charset, String r_charset) {
    return post(url, p_charset, r_charset, "");
  }

  /**
   * 按照charset格式编码读取
   * 
   * @param url
   * @param p_charset 发送编码
   * @param r_charset 接收编码
   * @param params 参数：id=1&type=0
   * @return
   */
  public static String post(String url, String p_charset, String r_charset, String params) {
    return post(url, p_charset, r_charset, params.getBytes());
  }

  /**
   * 按照charset格式编码读取
   * 
   * @param url
   * @param p_charset 发送编码
   * @param r_charset 接收编码
   * @param data
   * @return
   */
  public static String post(String url, String p_charset, String r_charset, byte[] data) {
    String target = "";
    try {
      URL add = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) add.openConnection();
      connection.setDoOutput(true);// 设置长连接
      connection.setDoInput(true);
      connection.setUseCaches(false);// 设置非缓存 避免多次请求不能取到最新数据
      connection.setRequestMethod("POST");// 请求方式
      connection.setRequestProperty("Charset", p_charset);// 请求编码
      connection.setConnectTimeout(TIME_OUT);// 响应超时时长
      connection.getOutputStream().write(data);// 发送字节流的请求
      connection.getOutputStream().flush();// 清空字节流
      connection.getOutputStream().close();// 关闭请求流
      // 获取响应字节流
      InputStream stream = connection.getInputStream();
      // 将流转换成reder
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, r_charset));
      String temp = null;
      while ((temp = reader.readLine()) != null) {
        target += temp;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return target;
  }

  /**
   * 默认按照UTF-8读取
   * 
   * @param url
   * @param params post数据
   * @param reqProperty RequestProperty包头设置参数
   * @return
   */
  public static String post(String url, String params, Map<String, String> reqsProperty) {
    return post(url, "UTF-8", params.getBytes(), reqsProperty);
  }

  /**
   * 按照charset格式编码读取
   * 
   * @param url
   * @param p_charset 发送编码
   * @param r_charset 接收编码
   * @param data post数据
   * @param reqsProperty RequestProperty包头设置参数
   * @return
   */
  public static String post(String url, String r_charset, byte[] data,
      Map<String, String> reqsProperty) {
    String target = "";
    try {
      URL add = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) add.openConnection();
      connection.setDoOutput(true);// 设置长连接
      connection.setDoInput(true);
      connection.setUseCaches(false);// 设置非缓存 避免多次请求不能取到最新数据
      connection.setRequestMethod("POST");// 请求方式
      if (!reqsProperty.isEmpty()) {
        @SuppressWarnings("rawtypes")
        Iterator ite = reqsProperty.keySet().iterator();
        while (ite.hasNext()) {
          String key = ite.next() + "";
          connection.setRequestProperty(key, reqsProperty.get(key) + "");
        }
      }
      connection.setConnectTimeout(TIME_OUT);// 响应超时时长
      connection.getOutputStream().write(data);// 发送字节流的请求
      connection.getOutputStream().flush();// 清空字节流
      connection.getOutputStream().close();// 关闭请求流
      // 获取响应字节流
      InputStream stream = connection.getInputStream();
      // 将流转换成reder
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, r_charset));
      String temp = null;
      while ((temp = reader.readLine()) != null) {
        target += temp;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return target;
  }



  /**
   * 默认按照UTF-8读取
   * 
   * @param url
   * @return
   */
  public static String get(String url) {
    return get(url, "UTF-8", "UTF-8", "");
  }

  /**
   * 按照charset格式编码读取
   * 
   * @param url
   * @param p_charset 发送编码
   * @param r_charset 接收编码
   * @param params 参数：id=1&type=0
   * @return
   */
  public static String get(String url, String p_charset, String r_charset, String params) {
    return get(url, p_charset, r_charset, params.getBytes());
  }

  /**
   * 按照charset格式编码读取
   * 
   * @param url
   * @param p_charset 发送编码
   * @param r_charset 接收编码
   * @param data
   * @return
   */
  public static String postJson(String url, String p_charset, String r_charset, String params) {
    LogUtil.debug(ApiUtils.class, "post", "url:%s", url);
    LogUtil.debug(ApiUtils.class, "post", "params:%s", params);
    String target = "";
    try {
      URL add = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) add.openConnection();
      connection.setDoOutput(true);// 设置长连接
      connection.setDoInput(true);
      connection.setUseCaches(false);// 设置非缓存 避免多次请求不能取到最新数据
      connection.setRequestMethod("POST");// 请求方式
      connection.setRequestProperty("Charset", p_charset);// 请求编码
      connection.setConnectTimeout(TIME_OUT);// 响应超时时长
      connection.setRequestProperty("Content-Type", "application/json");
      connection.getOutputStream().write(params.getBytes());// 发送字节流的请求
      connection.getOutputStream().flush();// 清空字节流
      connection.getOutputStream().close();// 关闭请求流
      // 获取响应字节流
      InputStream stream = connection.getInputStream();
      // 将流转换成reader
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, r_charset));
      String temp = null;
      while ((temp = reader.readLine()) != null) {
        target += temp;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return target;
  }

  /**
   * 按照charset格式编码读取
   * 
   * @param url
   * @param p_charset 发送编码
   * @param r_charset 接收编码
   * @param data get数据
   * @param reqsProperty RequestProperty包头设置参数
   * @return
   */
  public static String get(String url, String p_charset, String r_charset, byte[] data) {
    String target = "";
    try {
      URL add = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) add.openConnection();
      connection.setDoOutput(true);// 设置长连接
      connection.setDoInput(true);
      connection.setUseCaches(false);// 设置非缓存 避免多次请求不能取到最新数据
      connection.setRequestMethod("GET");// 请求方式
      connection.setRequestProperty("Charset", p_charset);// 请求编码
      connection.setConnectTimeout(TIME_OUT);// 响应超时时长
      connection.getOutputStream().write(data);// 发送字节流的请求
      connection.getOutputStream().flush();// 清空字节流
      connection.getOutputStream().close();// 关闭请求流
      // 获取响应字节流
      InputStream stream = connection.getInputStream();
      // 将流转换成reder
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, r_charset));
      String temp = null;
      while ((temp = reader.readLine()) != null) {
        target += temp;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return target;
  }

  /**
   * 默认按照UTF-8读取
   * 
   * @param url
   * @return
   */
  public static String get(String url, String apiKey) {
    return getWithHeader(url, "UTF-8", "UTF-8", apiKey);
  }

  /**
   * 按照charset格式编码读取
   * 
   * @param url
   * @param p_charset 发送编码
   * @param r_charset 接收编码
   * @param data get数据
   * @param reqsProperty RequestProperty包头设置参数
   * @return
   */
  public static String getWithHeader(String url, String p_charset, String r_charset, String apiKey) {
    String target = "";
    try {
      URL add = new URL(url);
      HttpURLConnection connection = (HttpURLConnection) add.openConnection();
      // connection.setDoOutput(true);// 设置长连接
      // connection.setDoInput(true);
      // connection.setUseCaches(false);// 设置非缓存 避免多次请求不能取到最新数据
      connection.setRequestMethod("GET");// 请求方式
      // 填入apikey到HTTP header
      connection.setRequestProperty("apikey", apiKey);
      // connection.setRequestProperty("Charset", p_charset);// 请求编码
      // connection.setConnectTimeout(TIME_OUT);// 响应超时时长
      // connection.getOutputStream().write(data);// 发送字节流的请求
      // connection.getOutputStream().flush();// 清空字节流
      // connection.getOutputStream().close();// 关闭请求流
      // 获取响应字节流
      InputStream stream = connection.getInputStream();
      // 将流转换成reder
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream, r_charset));
      String temp = null;
      while ((temp = reader.readLine()) != null) {
        target += temp;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return target;
  }

  // 此方法是POST请求上传的参数中包含本地图片信息File类型
  public static String post(Map<String, String> param, File file) throws Exception {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    String result = null;
    // HttpClient请求的相关设置，可以不用配置，用默认的参数，这里设置连接和超时时长(毫秒)
    RequestConfig config =
        RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
    try {
      HttpPost httppost = new HttpPost(param.get("url"));
      // FileBody封装File类型的参数
      FileBody bin = new FileBody(file);
      // StringBody封装String类型的参数
      StringBody keyBody = new StringBody(param.get("key"), ContentType.TEXT_PLAIN);
      StringBody typeBody = new StringBody(param.get("cardType"), ContentType.TEXT_PLAIN);
      // addPart将参数传入，并指定参数名称
      HttpEntity reqEntity =
          MultipartEntityBuilder.create().addPart("pic", bin).addPart("key", keyBody)
              .addPart("cardType", typeBody).build();
      httppost.setEntity(reqEntity);
      httppost.setConfig(config);
      // 执行网络请求并返回结果
      response = httpClient.execute(httppost);
      HttpEntity resEntity = response.getEntity();
      if (resEntity != null) {
        result = ConvertStreamToString(resEntity.getContent(), "UTF-8");
      }
      EntityUtils.consume(resEntity);
    } finally {
      response.close();
      httpClient.close();
    }
    // 得到的是JSON类型的数据需要第三方解析JSON的jar包来解析
    return result;
  }

  // 此方法是把传进的字节流转化为相应的字符串并返回，此方法一般在网络请求中用到
  public static String ConvertStreamToString(InputStream is, String charset) throws Exception {
    StringBuilder sb = new StringBuilder();
    try (InputStreamReader inputStreamReader = new InputStreamReader(is, charset)) {
      try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
        String line = null;
        while ((line = reader.readLine()) != null) {
          sb.append(line).append("\r\n");
        }
      }
    }
    return sb.toString();
  }


}
