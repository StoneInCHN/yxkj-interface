package com.yxkj.aspect;

import java.lang.reflect.Type;

public class UserParam {

  private String userId;

  private String userName;

  private Type returnType;

  private String token;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


}
