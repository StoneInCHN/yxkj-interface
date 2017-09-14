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
      beanMap.put("desc", "aop token为null验证失败");
      return response;// 抛出未认证的错误
    } else {
      Claims claims = TokenUtil.parseJWT(authorizationHeader);
      if (claims == null) {
        Class returnTypeClass = (Class) userParam.getReturnType();
        Object response = returnTypeClass.newInstance();
        BeanMap beanMap = BeanMap.create(response);
        beanMap.put("code", CommonAttributes.FAIL_TOKEN_AUTH);
        beanMap.put("desc", "token非法或失效");
        return response;
      } else {
        if (!userParam.getUserId().toString().equals(claims.getId())) {
          Class returnTypeClass = (Class) userParam.getReturnType();
          Object response = returnTypeClass.newInstance();
          BeanMap beanMap = BeanMap.create(response);
          beanMap.put("code", CommonAttributes.FAIL_TOKEN_AUTH);
          beanMap.put("desc", "token,id不匹配");
          return response;
        }
      }
    }
    // UserParam userParam = getControllerMethodParam(joinPoint);
    // String validFlag = "true";
    // EndUser endUser = endUserService.find(userParam.getUserId());
    // if (CheckUserType.ENDUSER.equals(userParam.getUserType())) {
    // if (endUser == null || AccountStatus.DELETE.equals(endUser.getAccountStatus())
    // || AccountStatus.LOCKED.equals(endUser.getAccountStatus())) {
    // validFlag = "userInvalid";
    // }
    // } else if (CheckUserType.SELLER.equals(userParam.getUserType())) {
    // if (endUser == null || AccountStatus.DELETE.equals(endUser.getAccountStatus())
    // || AccountStatus.LOCKED.equals(endUser.getAccountStatus())) {
    // validFlag = "userInvalid";
    // } else {
    // Set<Seller> sellers = endUser.getSellers();
    // if (CollectionUtils.isEmpty(sellers)) {
    // validFlag = "sellerNoexist";
    // } else {
    // Seller seller = null;
    // for (Seller s : sellers) {
    // if (!AccountStatus.DELETE.equals(s.getAccountStatus())) {
    // seller = s;
    // break;
    // }
    // }
    // if (seller == null) {
    // validFlag = "sellerNoexist";
    // } else {
    // if (AccountStatus.DELETE.equals(seller.getAccountStatus())
    // || AccountStatus.LOCKED.equals(seller.getAccountStatus())) {
    // validFlag = "sellerInvalid";
    // }
    // }
    //
    // }
    // }
    //
    // }
    //
    //
    // if (!"true".equals(validFlag)) {
    // Class returnTypeClass = (Class) userParam.getReturnType();
    // Object response = returnTypeClass.newInstance();
    // BeanMap beanMap = BeanMap.create(response);
    // beanMap.put("code", CommonAttributes.USER_INVALID);
    // String desc = "";
    // if ("userInvalid".equals(validFlag)) {
    // desc = Message.warn("rebate.user.invalid").getContent();
    // } else if ("sellerNoexist".equals(validFlag)) {
    // desc = Message.warn("rebate.seller.noexist").getContent();
    // } else if ("sellerInvalid".equals(validFlag)) {
    // desc = Message.warn("rebate.seller.invalid").getContent();
    // }
    // beanMap.put("desc", desc);
    // return response;
    // }
    //
    // /**
    // * 验证登录token：是否登录超时
    // */
    // String userToken = endUserService.getEndUserToken(userParam.getUserId());
    // if (!TokenGenerator.isTokenTimeout(userParam.getToken(), userToken)) {
    // Class returnTypeClass = (Class) userParam.getReturnType();
    // Object response = returnTypeClass.newInstance();
    // BeanMap beanMap = BeanMap.create(response);
    // beanMap.put("code", CommonAttributes.FAIL_TOKEN_TIMEOUT);
    // beanMap.put("desc", Message.error("rebate.user.token.timeout").getContent());
    // return response;
    // }
    //
    // /**
    // * 验证登录token：账号是否在其它设备登录
    // *
    // */
    // if (!TokenGenerator.isTokenAuth(userParam.getToken(), userToken)) {
    // Class returnTypeClass = (Class) userParam.getReturnType();
    // Object response = returnTypeClass.newInstance();
    // BeanMap beanMap = BeanMap.create(response);
    // beanMap.put("code", CommonAttributes.FAIL_TOKEN_AUTH);
    // beanMap.put("desc", Message.error("rebate.user.token.auth").getContent());
    // return response;
    // }
    //
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
            Long userId = (Long) beanMap.get("userId");
            // String token = (String) beanMap.get("token");
            params.setUserId(userId);
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
