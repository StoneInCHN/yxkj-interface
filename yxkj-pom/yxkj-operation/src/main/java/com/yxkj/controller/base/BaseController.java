package com.yxkj.controller.base;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.beans.DateEditor;
import com.yxkj.beans.Message;
import com.yxkj.beans.Setting;
import com.yxkj.common.log.LogUtil;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.utils.ExportExcel;
import com.yxkj.utils.SettingUtils;
import com.yxkj.utils.SpringUtils;
import com.yxkj.utils.TimeUtils;


public class BaseController {
	
  public static Setting setting = SettingUtils.get();
  /** 错误视图 */
  protected static final String ERROR_VIEW = "/common/error";

  /** 错误消息 */
  protected static final Message ERROR_MESSAGE = Message.error("admin.message.error");

  /** 成功消息 */
  protected static final Message SUCCESS_MESSAGE = Message.success("admin.message.success");

  /** "验证结果"参数名称 */
  private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

  @Resource(name = "validator")
  private Validator validator;
  
  @Resource(name = "taskExecutor")
  private Executor threadPoolExecutor;

  /**
   * 数据绑定
   * 
   * @param binder WebDataBinder
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    binder.registerCustomEditor(Date.class, new DateEditor(true));
  }

  /**
   * 数据验证
   * 
   * @param target 验证对象
   * @param groups 验证组
   * @return 验证结果
   */
  protected boolean isValid(Object target, Class<?>... groups) {
    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
    if (constraintViolations.isEmpty()) {
      return true;
    } else {
      RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
      requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations,
          RequestAttributes.SCOPE_REQUEST);
      return false;
    }
  }

  /**
   * 数据验证
   * 
   * @param type 类型
   * @param property 属性
   * @param value 值
   * @param groups 验证组
   * @return 验证结果
   */
  protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
    Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
    if (constraintViolations.isEmpty()) {
      return true;
    } else {
      RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
      requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations,
          RequestAttributes.SCOPE_REQUEST);
      return false;
    }
  }


  /**
   * 获取国际化消息
   * 
   * @param code 代码
   * @param args 参数
   * @return 国际化消息
   */
  protected String message(String code, Object... args) {
    return SpringUtils.getMessage(code, args);
  }

  /**
   * 添加瞬时消息
   * 
   * @param redirectAttributes RedirectAttributes
   * @param message 消息
   */
  /*
   * protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) { if
   * (redirectAttributes != null && message != null) {
   * redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME,
   * message); } }
   */

  /**
   * 添加日志
   * 
   * @param content 内容
   */
  protected void addLog(String content) {
    /*
     * if (content != null) { RequestAttributes requestAttributes =
     * RequestContextHolder.currentRequestAttributes();
     * requestAttributes.setAttribute(Log.LOG_CONTENT_ATTRIBUTE_NAME, content,
     * RequestAttributes.SCOPE_REQUEST); }
     */
  }
  /**
   * 
   * Controller运行时异常统一处理
   * @param runtimeException
   * @author luzhang
   */
  @ExceptionHandler(RuntimeException.class)
  public @ResponseBody BaseResponse runtimeException(HttpServletRequest request,
		  RuntimeException runtimeException) {
	  
    BaseResponse response = new BaseResponse();
    if (runtimeException.getMessage().indexOf("Token") >= 0) {
    	response.setCode(CommonAttributes.FAIL_TOKEN_TIMEOUT);
	}else {
		response.setCode(CommonAttributes.FAIL_COMMON);
	}    
    response.setDesc(runtimeException.getMessage());
    
    String responseResult = JSONObject.toJSONString(response);
    LogUtil.debug(BaseController.class, "runtimeException", "response = %s", responseResult);
    
    return response;
  }
  /**
   * 导出数据到Excel
   * 
   * @param response
   * @param baseEntityList 源集合
   * @param headers 需要导出的字段
   * @param headersName 字段对应列的列名
   * @author luzhang
   */
  protected void exportListToExcel(HttpServletResponse response,
      List<Map<String, String>> eventRecordMapList, String title, String[] headers,
      String[] headersName) {
    if (StringUtils.isBlank(title)) {
      title = "YXKJ_DATA";
    }
    if (headers != null && headersName != null && headers.length == headersName.length) {
      JSONArray jsonArray = new JSONArray();
      for (int i = 0; i < headersName.length; i++) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("headerName", headersName[i]);
        jsonObject.put("header", headers[i]);
        jsonArray.add(jsonObject);
      }

      try {
        response.setContentType("octets/stream");
        // 导出文件名
        String filename = title + "_" + TimeUtils.getDateFormatString("yyyyMMddHHmmss", new Date());
        response.addHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
        OutputStream out = response.getOutputStream();// 获得输出流

        Object locker = new Object();//当前主线程的一把锁

        ExportExcel ex = new ExportExcel(title, jsonArray, eventRecordMapList, out,locker);// 开启一个导出数据的线程

        synchronized (locker) {
          threadPoolExecutor.execute(ex); // 加入到线程池中执行
          locker.wait();//释放对象锁的控制，主线程等待，当且仅当其他线程notify该对象锁，主线程才可以继续执行下去
        }

        out.flush();
        out.close();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  /**
   * 获取请求参数中对应参数的value
   * @param requestParam
   * @param key
   * @author luzhang
   */
  protected String getReqPram(String requestParam, String key){
	  if (requestParam != null && requestParam.indexOf(key) >= 0) {
		  String[] paramArray = requestParam.split("&");
		  for (String param : paramArray) {
			if (param.indexOf("=") >= 0) {
				String[] keyValue = param.split("=");
				if (keyValue[0].equalsIgnoreCase(key)) {
					return keyValue[1];
				}
			}
		  }
	  }
	  return null;
  }
}
