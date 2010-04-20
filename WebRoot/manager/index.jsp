<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int port = request.getServerPort();
	String portStr = "";
	if (port!=80)
	    portStr=":"+port;
	String basePath = request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath() + "/";
%>
<%@ taglib prefix="decorator" uri="/WEB-INF/tld/sitemesh-decorator.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>TinyCms测试环境</title>
	<script type="text/javascript" src="<%=basePath%>common/mainLayout.js"></script>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
</head>
<body> 
	<div id="header"><h1>Ext Layout Browser</h1></div>

</body>
</html>