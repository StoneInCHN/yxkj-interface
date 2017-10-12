<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
支付宝参数:  公司ID:<%= request.getParameter("compId")%><br/>
商品ID:<%= request.getParameter("goodsId")%><br/>
商品集合:<%= request.getParameter("gl")%><br/>
code:<%= request.getParameter("authCode")%><br/>
微信参数:   公司ID<%= request.getParameter("compId")%><br/>
商品ID<%= request.getParameter("goodsId")%><br/>
商品集合:<%= request.getParameter("gl")%><br/>
code: <%= request.getParameter("authCode")%><br/>
</body>
</html>