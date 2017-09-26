package com.yxkj.shelf.aspect;

import java.lang.reflect.Type;

public class UserParam {



  private Long userId;

  private Type returnType;

  private String token;


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  public Type getReturnType() {
    return returnType;
  }

  public void setReturnType(Type returnType) {
    this.returnType = returnType;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


}
