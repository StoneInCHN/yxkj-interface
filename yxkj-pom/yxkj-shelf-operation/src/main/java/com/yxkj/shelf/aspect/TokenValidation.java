package com.yxkj.shelf.aspect;

import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.yxkj.shelf.common.log.LogUtil;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.utils.HttpServletRequestUtils;
import com.yxkj.shelf.utils.TokenUtil;


/**
 * 
 * @author luzhang
 * 切面组件: 验证Token
 *
 */
@Aspect
@Component 
public class TokenValidation {
	   
    @Autowired
    private HttpServletRequest request;           
    
    //切入点
    @Pointcut( value = "execution(* com.yxkj.shelf.controller.admin.*Controller.*(..))" )
    public void pointcut(){}
    
    //前置通知
    @Before("pointcut()")  
    public void before(){} 
    
    //后置通知
    @After("pointcut()")  
    public void after(){} 
    
    //返回通知
    @AfterReturning("pointcut()")
    public void afterReturning(){}
    
    //异常通知
    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) throws Throwable {
      LogUtil.debug(TokenValidation.class, "afterThrowing", joinPoint.toString()  + "Throwing Exception " + "\t" + ex.getMessage());
      throw ex;
    }
    
    //环绕通知
    @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

      long start = System.nanoTime();         

      //获取请求参数
      String requestParam = HttpServletRequestUtils.getRequestParam(request, "UTF-8");   
      LogUtil.debug(TokenValidation.class, "aroundAdvice", "request = %s", requestParam);
      
      //获取token, 存放在header的 X-Auth-Token里
      String xAuthToken = request.getHeader("X-Auth-Token");

      
      //开始验证token, 若验证失败，抛出运行时异常   统一在BaseController中处理
      if (xAuthToken == null) {
    	  //header中token为null
          throw new RuntimeException("Token缺失,请重新登录");
      }else {
          Claims claims = TokenUtil.parseJWT(xAuthToken);
          //token超时,抛出异常
          if (claims == null) {       	  
        	  throw new RuntimeException("Token过期,请重新登录");
          } 
          //用户ID与token不匹配
          else {
        	JSONObject jsonobject = (JSONObject)JSONObject.parse(requestParam);        	
        	String userName = getStrFromJSON(jsonobject, "userName"); 
            if (!claims.getId().equals(userName)) {
            	throw new RuntimeException("Token无效,请重新登录");
            }
          }
      }
        
      //执行原生接口,并获取接口返回对象
      Object responseObject = joinPoint.proceed();
      BaseResponse response = (BaseResponse)responseObject;    
      
      //打印返回结果
      String responseResult = JSONObject.toJSONString(response);      
      LogUtil.debug(TokenValidation.class, "aroundAdvice", "response = %s", responseResult);
      
      //请求总共用时
      LogUtil.debug(TokenValidation.class, "aroundAdvice", joinPoint.toString() + "\tUse time : " + (System.nanoTime() - start) + " ns!");
      
      return response;

    }
    /**
     * 操作JSON,根据key获取value
     * @param jsonobject
     * @param key
     * @return
     */
    private String getStrFromJSON(JSONObject jsonobject, String key){
      String value = null;
      if (jsonobject != null && jsonobject.containsKey(key)) {
        value = jsonobject.get(key).toString();
      }
      return value;
    }
    

}
