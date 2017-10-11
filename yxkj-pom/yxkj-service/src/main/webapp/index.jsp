<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<br/>
请求方法名:<%= request.getMethod() %><br/>
客户端信息:<%= request.getHeader("user-agent") %><br/>
<%
    String hearders = request.getHeader("user-agent");
	if(hearders.contains("AlipayClient")){
	  response.sendRedirect("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2017061707509713&scope=auth_user&redirect_uri=http://test.ybjcq.com/h5/item.jsp?orderId=1");
	}
	if(hearders.contains("MicroMessenger")){
	  response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3598eb401cb80f00&redirect_uri=http://test.ybjcq.com/h5/item.jsp&response_type=code&scope=snsapi_userinfo&state=1");
	}
%>
</body>
</html>