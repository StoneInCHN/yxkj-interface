package com.yxkj.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ToolsUtils implements Serializable {

  private static final long serialVersionUID = 7708468758384338657L;

  public static String getOrderSnByUUId(String machineId) {

    int hashCodeV = UUID.randomUUID().toString().hashCode();
    System.out.println(hashCodeV);
    if (hashCodeV < 0) {// 有可能是负数
      hashCodeV = -hashCodeV;
    }
    // 0 代表前面补充0
    // 13 代表长度为4
    // d 代表参数为正数型
    String orderId = machineId + String.format("%011d", hashCodeV);
    System.out.println(orderId);
    return orderId;
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
}
