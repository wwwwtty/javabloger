<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int port = request.getServerPort();
	String portStr = "";
	if (port!=80)
	    portStr=":"+port;
	String basePath = request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath() + "/";
%>
<html>
  <head>
    <title>功能模块管理</title>
    <script type="text/javascript" src="<%=basePath %>common/utils.js"></script>
    <script type="text/javascript" src="<%=basePath %>resource/fckeditor/fckeditor.js"></script>
    <script type="text/javascript" src="js/addNewsUI.js"></script>
    <script type="text/javascript" src="js/addNewsFN.js"></script>
  </head>
  <body>
  </body>
</html>