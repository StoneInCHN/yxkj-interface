package com.yxkj.utils;

public class test {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // System.out.println(TokenUtil.getJWTString("1", "2323", 1000000));
    String jwtToken =
        "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTA1MjA3ODI1LCJzdWIiOiIyMzIzIiwiZXhwIjoxNTA1MjA4ODI1fQ.FjJIYWmet_ey9ClCvZznc6TtAkYw0IFgmcPhCPSovlw";


    // TokenUtil.parseJWT(jwtToken);

    String jwtToken2 =
        "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTA1MjA3ODUyLCJzdWIiOiIyMzIzIiwiZXhwIjoxNTA1MjA4ODUyfQ.jBBLpRo2Po6J-eRl8Gg5SZmS-jztqNz-xuGtDi-JgrE";
    TokenUtil.parseJWT(TokenUtil.getJWTString("1", "2323"));
  }
}
