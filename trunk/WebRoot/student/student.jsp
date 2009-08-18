<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="table" uri="/table-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv=Content-Type content="text/html;charset=utf8">
		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">	
		<link media="print" type="text/css" href="<%=basePath %>share/css/displaytag.css" rel="stylesheet"/>
		</style>
	</head>

	<body>
		
	
	<table:table list="list">
		<table:tbody>
			<tr>
				<td><s:property value="loginName"/></td>
			</tr>
		</table:tbody>
	</table:table>
</html>
