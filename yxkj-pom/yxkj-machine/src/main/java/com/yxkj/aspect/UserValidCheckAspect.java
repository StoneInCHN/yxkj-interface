package com.yxkj.aspect;


import io.jsonwebtoken.Claims;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.beans.Message;
import com.yxkj.utils.TokenUtil;


@Aspect
@Component
public class UserValidCheckAspect {



  // @Resource(name = "endUserServiceImpl")
  // private EndUserService endUserService;

  // Controller层切点
  @Pointcut("@annotation(com.yxkj.aspect.UserValidCheck)")
  public void controllerAspect() {}


  /**
   * 前置通知 用于拦截Controller层记录用户的操作
   *
   * @param joinPoint 切点
   * @throws Throwable
   */
  @Around(value = "controllerAspect()")
  public @ResponseBody Object checkUserValid(ProceedingJoinPoint joinPoint) throws Throwable {
    UserParam userParam = getControllerMethodParam(joinPoint);
    // 获取头信息中的token
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String authorizationHeader = request.getHeader("X-Auth-Token");
    // 如果token为空抛出
    if (authorizationHeader == null) {
      Class returnTypeClass = (Class) userParam.getReturnType();
      Object response = returnTypeClass.newInstance();
      BeanMap beanMap = BeanMap.create(response);
      beanMap.put("code", CommonAttributes.FAIL_TOKEN_AUTH);
      beanMap.put("desc", Message.success("yxkj.token.invalid", "(ey00001)").getContent());
      return response;// 抛出未认证的错误
    } else {
      Claims claims = TokenUtil.parseJWT(authorizationHeader);
      if (claims == null) {
        Class returnTypeClass = (Class) userParam.getReturnType();
        Object response = returnTypeClass.newInstance();
        BeanMap beanMap = BeanMap.create(response);
        beanMap.put("code", CommonAttributes.FAIL_TOKEN_AUTH);
        beanMap.put("desc", Message.success("yxkj.token.invalid", "(ey00002)").getContent());
        return response;
      } else {// 用户ID与token不匹配
        if (!userParam.getUserName().equals(claims.getId())) {
          Class returnTypeClass = (Class) userParam.getReturnType();
          Object response = returnTypeClass.newInstance();
          BeanMap beanMap = BeanMap.create(response);
          beanMap.put("code", CommonAttributes.FAIL_TOKEN_TIMEOUT);
          beanMap.put("desc", Message.success("yxkj.user.token.nomatch").getContent());
          return response;
        }
      }
    }
    return joinPoint.proceed();

  }

  /**
   * 获取注解中对方法的参数信息 用于Controller层注解
   *
   * @param joinPoint 切点
   * @return 方法描述
   * @throws Exception
   */
  public static UserParam getControllerMethodParam(ProceedingJoinPoint joinPoint) throws Exception {
    UserParam params = new UserParam();
    // 获取目标类（controller）
    String targetName = joinPoint.getTarget().getClass().getName();
    // 获取目标方法（需要切入的方法）
    String methodName = joinPoint.getSignature().getName();
    // 获取目标方法参数
    Object[] arguments = joinPoint.getArgs();
    Class targetClass = Class.forName(targetName);
    Method[] methods = targetClass.getMethods();

    for (Method method : methods) {
      if (method.getName().equals(methodName)) {
        Class[] clazzs = method.getParameterTypes();
        if (clazzs.length == arguments.length) {
          // CheckUserType userType = method.getAnnotation(UserValidCheck.class).userType();
          Type returnType = method.getGenericReturnType();
          if (returnType instanceof ParameterizedType) {
            params.setReturnType(((ParameterizedType) returnType).getRawType());
          } else {
            params.setReturnType(returnType);
          }
          if (arguments.length > 0 && arguments[0] != null) {

            BeanMap beanMap = BeanMap.create(arguments[0]);
            String userName = (String) beanMap.get("userName");
            // String token = (String) beanMap.get("token");
            params.setUserName(userName);
            // params.setUserType(userType);
            // params.setToken(token);
          }
          break;
        }
      }
    }
    return params;
  }

}
