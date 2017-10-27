package com.yxkj.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.yxkj.beans.Setting;
import com.yxkj.common.log.LogUtil;


public class ToolsUtils implements Serializable {

  private static final long serialVersionUID = 7708468758384338657L;

  private static final String billDateFormat = "yyMMddHHmmss";

  private static Integer baseNo = 1;


  /**
   * 生成账单号 格式 yyMMddHHmmss-组织机构代码-(1-1000累加)
   * 
   * @return
   */
  public synchronized static String generateBillNo(String orgCode) {
    StringBuffer strBuffer = new StringBuffer();
    SimpleDateFormat sdf = new SimpleDateFormat(billDateFormat);
    String strDate = sdf.format(new Date());
    strBuffer.append(strDate);
    strBuffer.append(orgCode);
    // 订单尾号增自999时重置
    if (baseNo == 999) {
      baseNo = 1;
    }
    if (baseNo.toString().length() == 1) {
      strBuffer.append("00");
    }
    if (baseNo.toString().length() == 2) {
      strBuffer.append("0");
    }
    strBuffer.append(baseNo++);
    return strBuffer.toString();
  }

  /**
   * 在date的基础上加时间
   * 
   * @param date
   * @param field 比如Calendar.HOUR Calendar.SECOND
   * @param amount
   * @return
   */
  public static Date addTime(Date date, int field, int amount) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(field, amount);
    return calendar.getTime();
  }

  /**
   * 检查对象obj的成员变量是否都为null
   * 
   * @param obj
   * @return
   */
  public static boolean checkObjAllFieldNull(Object obj) {
    for (Field f : obj.getClass().getDeclaredFields()) {
      f.setAccessible(true);
      try {
        if (f.get(obj) != null) {
          return false;
        }
      } catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  /**
   * 对象字段toString显示方法
   * 
   * @param obj
   * @return
   */
  public static String entityToString(Object obj) {

    if (obj == null)
      return "null";
    StringBuffer sb = new StringBuffer();
    Class<?> clazz = obj.getClass();
    Field[] fields = clazz.getDeclaredFields();

    sb.append(clazz.getName() + "{");
    try {
      for (Field field : fields) {
        field.setAccessible(true);
        sb.append("\n  " + field.getName() + ":" + field.get(obj));
      }
    } catch (IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    }
    sb.append("\n}");
    return sb.toString();
  }
  /**
   * 发送短信
   * @param smsUrl
   * @param parameters
   * @return
   */
  public static String sendReq(String smsUrl, String parameters) {

	    StringBuffer response = new StringBuffer();
	    try {
	        LogUtil.debug(ToolsUtils.class, "sendReq", "Request API URL is : %s", smsUrl + parameters);

	      URL url = new URL(smsUrl);
	      HttpURLConnection con = (HttpURLConnection) url.openConnection();

	      // add reuqest header
	      con.setRequestMethod("GET");
	      con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	      con.setDoOutput(true);
	      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	      wr.write(parameters.getBytes());
	      // wr.writeBytes(parameters);
	      wr.flush();
	      wr.close();

	      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      String inputLine;

	      while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	      }
	      in.close();

	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    LogUtil.debug(ToolsUtils.class, "Response", "Response from API is : %s", response.toString());
	    return response.toString();
	}
  /**
   * 发送短信
   * @param smsUrl
   * @param parameters
   * @return
   */
	public static String sendSmsMsg(String mobile, String msg) {
	    try {
	      Setting setting = SettingUtils.get();
	      String smsUrl = setting.getSmsUrl();
	      String smsOrgId = setting.getSmsOrgId();
	      String smsUserName = setting.getSmsUserName();
	      String smsPwd = setting.getSmsPwd();
	      String message = URLEncoder.encode(msg, "UTF-8");
	      String url =
	          "Id=" + smsOrgId + "&Name=" + smsUserName + "&Psw=" + smsPwd + "&Message=" + message
	              + "&Phone=" + mobile + "&Timestamp=0";
	      String rs = sendReq(smsUrl, url);
	      return rs;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	}
	public static void main(String[] args){
	    try {
	    	  String mobile = "18280330380";
	    	  String msg = "DALU您好，恭喜您成为优比家的管家，您的账号为12345678910，初始密码为zxasfasdgfa，请尽快登陆管家系统修改您的密码，谢谢。";
		      String smsUrl = "http://124.172.234.157:8180/Service.asmx/SendMessage";
		      String smsOrgId = "300";
		      String smsUserName = "ybj";
		      String smsPwd = "123456";
		      String message = URLEncoder.encode(msg, "UTF-8");
		      String url =
		          "Id=" + smsOrgId + "&Name=" + smsUserName + "&Psw=" + smsPwd + "&Message=" + message
		              + "&Phone=" + mobile + "&Timestamp=0";
		      String rs = sendReq(smsUrl, url);
		      System.out.println(rs);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
}
