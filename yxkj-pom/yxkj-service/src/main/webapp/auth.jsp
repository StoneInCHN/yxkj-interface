<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.yxkj.beans.Setting,com.yxkj.utils.SettingUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<br/>
<%
    String hearders = request.getHeader("user-agent");
	Setting setting = SettingUtils.get();
	String redirectUrl = "";
	if(hearders.contains("AlipayClient")){
	  String sys = request.getParameter("sys");
	  if("shelf".equals(sys)){
	    redirectUrl = setting.getShelfSysUrl()+"?type=alipay&compId="+request.getParameter("compId")+"&goodsId="+request.getParameter("goodsId")+"&authCode="+request.getParameter("auth_code");
	  }
	  else if("cntr".equals(sys)){
	    redirectUrl = setting.getCntrSysUrl()+"?type=alipay&imei="+request.getParameter("imei")+"&gl="+request.getParameter("gList")+"&authCode="+request.getParameter("auth_code");
	  }else if ("cntrScan".equals(sys)){
		  redirectUrl = setting.getCntrScanSysUrl()+"?type=alipay&imei="+request.getParameter("imei")+"&cntrId="+request.getParameter("cntrId")+"&authCode="+request.getParameter("auth_code");
	  }
	}
	if(hearders.contains("MicroMessenger")){
	  String[] state = request.getParameter("state").split(",");
	  if("shelf".equals(state[0])){
	    redirectUrl = setting.getShelfSysUrl()+"?type=wx&compId="+state[1]+"&goodsId="+state[2]+"&authCode="+request.getParameter("code");
	  }
	  else if("cntr".equals(state[0])){
	    redirectUrl = setting.getCntrSysUrl()+"?type=wx&imei="+state[1]+"&gl="+state[2]+"&authCode="+request.getParameter("code");
	  }
	  else if("cntrGoods".equals(state[0])){
	    redirectUrl = setting.getCntrScanSysUrl()+"?type=wx&imei="+state[1]+"&cntrId="+state[2]+"&authCode="+request.getParameter("code");
	  }
	}
	response.sendRedirect(redirectUrl);
%>
</body>
</html>
