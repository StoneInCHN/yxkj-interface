package com.yxkj.controller.base;



import com.yxkj.beans.Setting;
import com.yxkj.utils.SettingUtils;


/**
 *
 * 类说明
 * 
 * @author Andrea
 * @version 创建时间：2017年10月10日 下午4:25:15
 * 
 */
public class BaseController {
  public static Setting setting = SettingUtils.get();

  // /**
  // * 数据绑定
  // *
  // * @param binder WebDataBinder
  // */
  // @InitBinder
  // protected void initBinder(WebDataBinder binder) {
  // binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  // binder.registerCustomEditor(Date.class, new DateEditor(true));
  // }

}
